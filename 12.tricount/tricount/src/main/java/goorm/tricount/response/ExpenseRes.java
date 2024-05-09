package goorm.tricount.response;

import goorm.tricount.domain.Expense;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpenseRes {

    private String title;
    private String userName;
    private String amount;
    private LocalDateTime date;

    public static ExpenseRes res(Expense expense) {
        ExpenseRes expenseRes = new ExpenseRes();
        expenseRes.title = expense.getExpenseName();
        expenseRes.userName = expense.getUser().getUserName();
        expenseRes.amount = String.valueOf(expense.getAmount());
        expenseRes.date = expense.getExpenseDate();
        return expenseRes;
    }
}
