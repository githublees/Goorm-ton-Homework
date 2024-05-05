package goorm.tricount.service;

import goorm.tricount.domain.Balance;
import goorm.tricount.domain.Expense;
import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.repository.BalanceRepository;
import goorm.tricount.repository.ExpenseRepository;
import goorm.tricount.repository.SettlementRepository;
import goorm.tricount.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BalanceService {

    private final SettlementRepository settlementRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;


    public List<Balance> getBalance(Long settlementId) {
        return balanceRepository.findAllBySettlementId(settlementId);
    }

    @Transactional
    public List<Balance> createBalance(Long settlementId) {

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
        
        List<Balance> balances = new ArrayList<>();
        for (Long senderId : totalUserAmountMap.keySet()) {
            BigDecimal sendPayment = totalUserAmountMap.get(senderId).subtract(avgAmount);

            if (sendPayment.compareTo(BigDecimal.ZERO) < 0) {
                for (Long receiverId : totalUserAmountMap.keySet()) {

                    if (senderId == receiverId) continue;

                    BigDecimal receivePayment = totalUserAmountMap.get(receiverId).subtract(avgAmount);

                    if (receivePayment.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal minusReceive = sendPayment.multiply(new BigDecimal("-1"));
                        BigDecimal calcAmount = minusReceive.compareTo(receivePayment) < 0 ? minusReceive : receivePayment;
                        calcAmount = calcAmount.setScale(0, RoundingMode.HALF_UP);

                        User sender = userRepository.findOne(senderId);
                        User receiver = userRepository.findOne(receiverId);

                        Balance balance = Balance.createBalance(settlement,  senderId, sender.getUserName(), calcAmount, receiverId, receiver.getUserName());
                        balances.add(balance);

                        sendPayment.add(sendPayment).add(calcAmount);
                        if (sendPayment.compareTo(BigDecimal.ZERO) >= 0) {
                            break;
                        }
                    }
                }
            }
        }

        balanceRepository.saveAll(balances);
        return balances;
    }
}
