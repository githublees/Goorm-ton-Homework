package goorm.tricount.service;

import goorm.tricount.domain.Expense;
import goorm.tricount.domain.ExpenseStatus;
import goorm.tricount.domain.User;
import goorm.tricount.response.ExpenseRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class ExpenseServiceTest {

    @Autowired UserService userService;
    @Autowired SettlementService settlementService;
    @Autowired ExpenseService expenseService;

    @Test
    public void 지출_생성() throws Exception {
        //given
        User user = User.createUser("호성", "1234", "wlzhf");
        Long savedId = userService.save(user);
        Long settlementId = settlementService.joinCreateSettlement(savedId, "강릉 여행");
        expenseService.addExpense(settlementId, savedId, "아침", new BigDecimal("10000"));
        expenseService.addExpense(settlementId, savedId, "점심", new BigDecimal("10000"));

        //when
        List<ExpenseRes> expenses = expenseService.getExpenseList(settlementId);

        //then
        assertEquals(2, expenses.size());
        assertEquals("아침", expenses.get(0).getTitle());
        assertEquals("점심", expenses.get(1).getTitle());
    }

    @Test
    public void 지출_삭제() throws Exception {
        //given
        User user = User.createUser("호성", "1234", "wlzhf");
        Long savedId = userService.save(user);
        Long settlementId = settlementService.joinCreateSettlement(savedId, "강릉 여행");
        Long expense1 = expenseService.addExpense(settlementId, savedId, "아침", new BigDecimal("10000"));
        Long expense2 = expenseService.addExpense(settlementId, savedId, "점심", new BigDecimal("10000"));

        //when
        expenseService.deleteExpense(expense1);
        Expense find1 = expenseService.getExpense(expense1);
        Expense find2 = expenseService.getExpense(expense2);

        //then
        assertEquals(ExpenseStatus.DELETE, find1.getExpenseStatus());
        assertEquals(ExpenseStatus.ACTIVE, find2.getExpenseStatus());
    }
}