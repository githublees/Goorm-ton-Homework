package goorm.tricount.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Balance {

    @Id @GeneratedValue
    @Column(name = "BALANCE_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SETTLEMENT_ID")
    private Settlement settlement;

    private Long senderUserNo;
    private String senderUserName;
    private BigDecimal sendAmount;
    private Long receiverUserNo;
    private String receiverUserName;

    @Enumerated(EnumType.STRING)
    private BalanceStatus balanceStatus;

    //== 연관관계 메서드 ==//
    public void addBalance(Settlement settlement) {
        this.settlement = settlement;
        settlement.getBalances().add(this);
    }

    //== 생성 메서드 ==//
    public static Balance createBalance(Settlement settlement, Long senderUserNo, String senderUserName, BigDecimal sendAmount, Long receiverUserNo, String receiverUserName) {
        Balance balance = new Balance();
        balance.senderUserNo = senderUserNo;
        balance.senderUserName = senderUserName;
        balance.sendAmount = sendAmount;
        balance.receiverUserNo = receiverUserNo;
        balance.receiverUserName = receiverUserName;
        balance.addBalance(settlement);
        return balance;
    }

    public void delete() {
        this.balanceStatus = BalanceStatus.DELETE;
    }
}
