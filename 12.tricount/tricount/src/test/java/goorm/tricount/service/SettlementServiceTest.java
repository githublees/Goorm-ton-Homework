package goorm.tricount.service;

import goorm.tricount.domain.*;
import goorm.tricount.repository.SettlementRepository;
import goorm.tricount.response.SettlementRes;
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
class SettlementServiceTest {

    @Autowired UserService userService;
    @Autowired SettlementService settlementService;
    @Autowired SettlementRepository settlementRepository;
    @Autowired ExpenseService expenseService;

    @Test
    public void 정산_생성() throws Exception {
        //given
        User user = User.createUser("호성", "1234", "wlzhf");
        Long savedId = userService.save(user);
        Long settlementId = settlementService.joinCreateSettlement(savedId, "강릉 여행");

        //when
        SettlementRes find = settlementService.getSettlement(settlementId);

        //then
        assertEquals("강릉 여행", find.getSettlementTitle());
    }

    @Test
    public void 정산_리스트() throws Exception {
        //given
        User user = User.createUser("호성", "1234", "wlzhf");
        User user2 = User.createUser("은호", "1234", "sdfhj");
        User user3 = User.createUser("진규", "1234", "dfjks");
        Long savedId = userService.save(user);
        Long savedId2 = userService.save(user2);
        Long savedId3 = userService.save(user3);
        Long settlementId1 = settlementService.joinCreateSettlement(savedId, "강릉 여행");
        Long settlementId2 = settlementService.joinCreateSettlement(savedId, "기차 여행");
        Long settlementId3 = settlementService.joinCreateSettlement(savedId, "해외 여행");
        settlementService.joinSettlement(settlementId1, savedId2);
        settlementService.joinSettlement(settlementId3, savedId3);

        //when
        List<SettlementRes> settlements = settlementService.getSettlementList(savedId);

        //then
        assertEquals(3, settlements.size());
    }

    @Test
    public void 유저_초대() throws Exception {
        //given
        User user1 = User.createUser("호성", "1234", "wlzhf");
        User user2 = User.createUser("은호", "1234", "sdfhj");
        User user3 = User.createUser("진규", "1234", "dfjks");
        Long savedId1 = userService.save(user1);
        Long savedId2 = userService.save(user2);
        Long savedId3 = userService.save(user3);
        Long settlementId = settlementService.joinCreateSettlement(savedId1, "강릉 여행");
        settlementService.joinSettlement(settlementId, savedId2);
        settlementService.joinSettlement(settlementId, savedId3);

        //when
        SettlementRes find = settlementService.getSettlement(settlementId);

        //then
        assertEquals(3, find.getJoins().size());
    }

    @Test
    public void 정산_삭제() throws Exception {
        //given
        User user1 = User.createUser("호성", "1234", "wlzhf");
        Long savedId1 = userService.save(user1);
        Long settlementId = settlementService.joinCreateSettlement(savedId1, "강릉 여행");

        //when
        settlementService.deleteSettlement(settlementId);

        Settlement find = settlementRepository.findOne(settlementId);

        //then
        assertEquals(SettlementStatus.DELETE, find.getSettlementStatus());
    }

    @Test
    public void 정산_삭제에_의한_유저_탈퇴() throws Exception {
        User user1 = User.createUser("호성", "1234", "wlzhf");
        User user2 = User.createUser("은호", "1234", "sdfhj");
        User user3 = User.createUser("진규", "1234", "dfjks");
        Long savedId1 = userService.save(user1);
        Long savedId2 = userService.save(user2);
        Long savedId3 = userService.save(user3);
        Long settlementId = settlementService.joinCreateSettlement(savedId1, "강릉 여행");
        settlementService.joinSettlement(settlementId, savedId2);
        settlementService.joinSettlement(settlementId, savedId3);

        //when
        settlementService.deleteSettlement(settlementId);
        Settlement find = settlementRepository.findOne(settlementId);

        //then
        for (Join join : find.getJoins()) {
            assertEquals(JoinStatus.LEAVE, join.getJoinStatus());
        }
    }

    @Test
    public void 정산_삭제에_의한_지출_삭제() throws Exception {
        //given
        User user1 = User.createUser("호성", "1234", "wlzhf");
        Long savedId1 = userService.save(user1);
        Long settlementId = settlementService.joinCreateSettlement(savedId1, "강릉 여행");
        Long expanse1 = expenseService.addExpense(settlementId, savedId1, "아침", new BigDecimal("20000"));
        Long expanse2 = expenseService.addExpense(settlementId, savedId1, "점심", new BigDecimal("20000"));

        //when
        settlementService.deleteSettlement(settlementId);
        Settlement find = settlementRepository.findOne(settlementId);

        //then
        for (Expense expense : find.getExpenses()) {
            assertEquals(ExpenseStatus.DELETE, expense.getExpenseStatus());
        }
    }
}