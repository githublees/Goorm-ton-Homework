package goorm.tricount.service;

import goorm.tricount.domain.Balance;
import goorm.tricount.domain.Settlement;
import goorm.tricount.domain.User;
import goorm.tricount.response.BalanceRes;
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
class BalanceServiceTest {

    @Autowired BalanceService balanceService;
    @Autowired ExpenseService expenseService;
    @Autowired SettlementService settlementService;
    @Autowired UserService userService;

    @Test
    public void 정산_결과_작성() throws Exception {
        //given
        Long userId1 = userService.save(User.createUser("hoseong", "password", "유저1"));
        Long userId2 = userService.save(User.createUser("jiseop", "password", "유저2"));
        Long userId3 = userService.save(User.createUser("heajong", "password", "유저3"));
        Long userId4 = userService.save(User.createUser("saldfj", "password", "유저4"));

        Long settlementId = settlementService.joinCreateSettlement(userId1, "강릉 여행");
        settlementService.joinSettlement(settlementId, userId2);
        settlementService.joinSettlement(settlementId, userId3);
        settlementService.joinSettlement(settlementId, userId4);

        expenseService.addExpense(settlementId, userId1, "기차표 예매", new BigDecimal("150000"));
        expenseService.addExpense(settlementId, userId2, "숙소 값", new BigDecimal("30000"));
        expenseService.addExpense(settlementId, userId3, "첫날 점심", new BigDecimal("80000"));
        expenseService.addExpense(settlementId, userId4, "첫날 저녁", new BigDecimal("100000"));

        //when
        List<BalanceRes> balances = balanceService.createBalance(settlementId);

        //then
        assertEquals(balances.get(0).getSenderUserName(), "jiseop");
        assertEquals(balances.get(0).getSendAmount(),"60000");
        assertEquals(balances.get(0).getReceiverUserName(), "hoseong");
        assertEquals(balances.get(1).getSenderUserName(), "heajong");
        assertEquals(balances.get(1).getSendAmount(), "10000");
        assertEquals(balances.get(1).getReceiverUserName(), "saldfj");
    }
}