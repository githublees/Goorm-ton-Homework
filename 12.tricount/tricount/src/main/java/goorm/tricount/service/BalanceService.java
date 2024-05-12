package goorm.tricount.service;

import goorm.tricount.domain.Balance;
import goorm.tricount.domain.Expense;
import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.repository.BalanceRepository;
import goorm.tricount.repository.ExpenseRepository;
import goorm.tricount.repository.SettlementRepository;
import goorm.tricount.repository.UserRepository;
import goorm.tricount.response.BalanceRes;
import goorm.tricount.response.ExpenseRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BalanceService {

    private final SettlementRepository settlementRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;


    public void deleteBalance(Long settlementId) {
        List<Balance> balances = balanceRepository.findAllBySettlementId(settlementId);
        for (Balance balance : balances) {
            balance.delete();
        }
    }

    @Transactional
    public List<BalanceRes> createBalance(Long settlementId) {

        Settlement settlement = settlementRepository.findOne(settlementId);
        List<Expense> expenses = expenseRepository.findAllBySettlementId(settlementId);

        Map<Long, BigDecimal> totalUserAmountMap = new HashMap<>();
        for (Expense expense : expenses) {
            Long userId = expense.getUser().getId();
            BigDecimal amount = expense.getAmount();
            totalUserAmountMap.put(
                    userId,
                    totalUserAmountMap.getOrDefault(userId, BigDecimal.ZERO).add(amount));
        }

        BigDecimal avgAmount = BigDecimal.ONE;
        if (settlement.getJoins().size() != 0) {
            avgAmount = settlement.getAmount()
                    .divide(BigDecimal.valueOf(settlement.getJoins().size()), RoundingMode.HALF_UP)
                    .setScale(0, RoundingMode.HALF_UP);
        }

        List<BalanceRes> balances = new ArrayList<>();
        for (Long senderId : totalUserAmountMap.keySet()) {
            BigDecimal sendPayment = totalUserAmountMap.get(senderId).subtract(avgAmount);

            if (sendPayment.compareTo(BigDecimal.ZERO) < 0) {
                for (Long receiverId : totalUserAmountMap.keySet()) {

                    if (senderId == receiverId) continue;

                    BigDecimal receivePayment = totalUserAmountMap.get(receiverId).subtract(avgAmount);

                    if (receivePayment.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal minusReceive = sendPayment.multiply(new BigDecimal("-1"));
                        BigDecimal calcAmount = minusReceive.compareTo(receivePayment) <= 0 ? minusReceive : receivePayment;
                        calcAmount = calcAmount.setScale(0, RoundingMode.HALF_UP);

                        User sender = userRepository.findOne(senderId);
                        User receiver = userRepository.findOne(receiverId);

                        Balance balance = Balance.createBalance(settlement, senderId, sender.getUserName(), calcAmount, receiverId, receiver.getUserName());
                        balanceRepository.save(balance);

                        balances.add(BalanceRes.res(balance));

                        totalUserAmountMap.put(receiverId, totalUserAmountMap.get(receiverId).subtract(calcAmount));
                        sendPayment = sendPayment.add(calcAmount);
                        if (sendPayment.compareTo(BigDecimal.ZERO) >= 0) {
                            break;
                        }
                    }
                }
            }
        }

        return balances;
    }

    @Transactional
    public List<BalanceRes> getStreamBalance(Long settlementId) {
        Map<User, BigDecimal> totalUserAmount = expenseRepository.findAllBySettlementId(settlementId)
                .stream().collect(Collectors.groupingBy(
                        expense -> expense.getUser(),
                        Collectors.mapping(
                                Expense::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        BigDecimal avgAmount = Optional.of(settlementRepository.findOne(settlementId))
                .map(s -> s.getAmount()
                        .divide(BigDecimal.valueOf(Math.max(1, s.getJoins().size())), RoundingMode.HALF_UP))
                        .orElse(BigDecimal.ONE);

        Map<User, BigDecimal> calcUserAmount = totalUserAmount.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, userBigDecimalEntry -> userBigDecimalEntry.getValue().subtract(avgAmount)));

        List<Map.Entry<User, BigDecimal>> receivers = calcUserAmount.entrySet().stream()
                .filter(userBigDecimalEntry -> userBigDecimalEntry.getValue().signum() > 0)
                .collect(Collectors.toList());

        List<Map.Entry<User, BigDecimal>> senders = calcUserAmount.entrySet().stream()
                .filter(userBigDecimalEntry -> userBigDecimalEntry.getValue().signum() < 0)
                .collect(Collectors.toList());

        List<BalanceRes> balances = new ArrayList<>();
        int receiverIndex = 0;
        int senderIndex = 0;
        while (receiverIndex < receivers.size() && senderIndex < senders.size()) {
            BigDecimal amountToTransfer = receivers.get(receiverIndex).getValue().add(senders.get(senderIndex).getValue());

            if (amountToTransfer.signum() < 0) {
                balances.add(BalanceRes.res(
                        senders.get(senderIndex).getKey().getId(),
                        senders.get(senderIndex).getKey().getNickname(),
                        receivers.get(receiverIndex).getValue().abs(),
                        receivers.get(receiverIndex).getKey().getId(),
                        receivers.get(receiverIndex).getKey().getNickname()
                ));
                receivers.get(receiverIndex).setValue(BigDecimal.ZERO);
                senders.get(senderIndex).setValue(amountToTransfer);
                receiverIndex++;
            } else if (amountToTransfer.signum() > 0) {
                balances.add(BalanceRes.res(
                        senders.get(senderIndex).getKey().getId(),
                        senders.get(senderIndex).getKey().getNickname(),
                        senders.get(senderIndex).getValue().abs(),
                        receivers.get(receiverIndex).getKey().getId(),
                        receivers.get(receiverIndex).getKey().getNickname()
                ));
                receivers.get(receiverIndex).setValue(amountToTransfer);
                senders.get(senderIndex).setValue(BigDecimal.ZERO);
                senderIndex++;
            } else {
                balances.add(BalanceRes.res(
                        senders.get(senderIndex).getKey().getId(),
                        senders.get(senderIndex).getKey().getNickname(),
                        senders.get(senderIndex).getValue().abs(),
                        receivers.get(receiverIndex).getKey().getId(),
                        receivers.get(receiverIndex).getKey().getNickname()
                ));
                receivers.get(receiverIndex).setValue(BigDecimal.ZERO);
                senders.get(senderIndex).setValue(BigDecimal.ZERO);
                receiverIndex++;
                senderIndex++;
            }
        }

        return balances;
    }
}
