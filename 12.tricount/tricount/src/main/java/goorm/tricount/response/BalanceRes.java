package goorm.tricount.response;

import goorm.tricount.domain.Balance;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BalanceRes {

    private Long senderUserNo;
    private String senderUserName;
    private String sendAmount;
    private Long receiverUserNo;
    private String receiverUserName;

    public static BalanceRes res(Balance balance) {
        BalanceRes balanceRes = new BalanceRes();
        balanceRes.senderUserNo = balance.getSenderUserNo();
        balanceRes.senderUserName = balance.getSenderUserName();
        balanceRes.sendAmount = String.valueOf(balance.getSendAmount());
        balanceRes.receiverUserNo = balance.getReceiverUserNo();
        balanceRes.receiverUserName = balance.getReceiverUserName();
        return balanceRes;
    }
}
