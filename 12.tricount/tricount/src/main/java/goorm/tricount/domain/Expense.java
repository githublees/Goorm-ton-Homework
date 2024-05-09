package goorm.tricount.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense {

    @Id @GeneratedValue
    @Column(name = "EXPENSE_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SETTLEMENT_ID")
    private Settlement settlement;

    private String expenseName;

    // Dto
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private BigDecimal amount;

    private LocalDateTime expenseDate;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus expenseStatus;

    //== 연관관계 메서드 ==//
    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
        settlement.getExpenses().add(this);
    }

    //== 생성 메서드 ==//
    public static Expense createExpense(Settlement settlement, User user, String expenseName, BigDecimal amount) {
        Expense expense = new Expense();
        expense.expenseName = expenseName;
        expense.amount = amount;
        expense.user = user;
        expense.expenseDate = LocalDateTime.now();
        expense.expenseStatus = ExpenseStatus.ACTIVE;
        expense.setSettlement(settlement);
        return expense;
    }

    //== 비즈니스 로직==//
    /**
     * 지출 삭제
     */
    public void delete() {
        this.expenseStatus = ExpenseStatus.DELETE;
    }
}
