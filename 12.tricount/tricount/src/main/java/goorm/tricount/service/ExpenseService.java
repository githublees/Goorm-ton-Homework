package goorm.tricount.service;

import goorm.tricount.domain.Expense;
import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.repository.ExpenseRepository;
import goorm.tricount.repository.SettlementRepository;
import goorm.tricount.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final SettlementRepository settlementRepository;

    @Transactional
    public Long addExpense(Long settlementId, Long userId, String expenseName, BigDecimal amount) {

        Settlement settlement = settlementRepository.findOne(settlementId);
        User user = userRepository.findOne(userId);

        Expense expense = Expense.createExpense(settlement, user, expenseName, amount);
        expenseRepository.save(expense);

        return expense.getId();
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.findOne(expenseId);
        expense.delete();
    }

    public List<Expense> getExpenseList(Long settlementId) {
        return expenseRepository.findAllBySettlementId(settlementId);
    }
}
