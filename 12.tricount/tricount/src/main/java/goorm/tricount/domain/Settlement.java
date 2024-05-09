package goorm.tricount.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Settlement {

    @Id @GeneratedValue
    @Column(name = "SETTLEMENT_ID")
    private Long id;

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL)
    private List<Join> joins = new ArrayList<>();

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL)
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "settlement", cascade = CascadeType.ALL)
    private List<Balance> balances = new ArrayList<>();

    private String settlementName;

    @Enumerated(EnumType.STRING)
    private SettlementStatus settlementStatus;

    //== 생성 메서드 ==//
    public static Settlement createSettlement(String settlementName) {
        Settlement settlement = new Settlement();
        settlement.settlementName = settlementName;
        settlement.settlementStatus = SettlementStatus.ACTIVE;
        return settlement;
    }

    //== 비즈니스 로직==//
    public void delete() {
        this.settlementStatus = SettlementStatus.DELETE;
        for (Join join : joins) {
            join.leave();
        }
        for (Expense expense : expenses) {
            expense.delete();
        }
        for (Balance balance : balances) {
            balance.delete();

        }
    }

    //== 조회 로직 ==//
    /**
     * 전체 지출
     */
    public BigDecimal getAmount() {
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(0, RoundingMode.HALF_UP);
    }
}
