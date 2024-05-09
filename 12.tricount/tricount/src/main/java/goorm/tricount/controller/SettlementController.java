package goorm.tricount.controller;

import goorm.tricount.request.SettleCreateReq;
import goorm.tricount.request.common.ApiResponse;
import goorm.tricount.response.SettlementRes;
import goorm.tricount.service.SettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SettlementController extends BaseController {

    private final SettlementService settlementService;


    @GetMapping("/settlements/{userId}")
    public ApiResponse settlementList(@PathVariable Long userId) {
        return makeResponse(settlementService.getSettlementList(userId));
    }

    @PostMapping("/settlement/{userId}")
    public ApiResponse createJoinSettlement(
            @PathVariable Long userId,
            @RequestBody SettleCreateReq settleCreateReq
    ) {
        return makeResponse(settlementService.joinCreateSettlement(userId, settleCreateReq.getTitle()));
    }

    @GetMapping("/settlement/{settlementId}")
    public ApiResponse getSettlement(@PathVariable Long settlementId) {
        return  makeResponse(settlementService.getSettlement(settlementId));
    }

    @PostMapping("/settlement/{userId}/{settlementId}")
    public ApiResponse joinSettlement(
            @PathVariable Long userId,
            @PathVariable Long settlementId
    ) {
        settlementService.joinSettlement(settlementId, userId);
        return makeResponse("Join Successful");
    }

//    @GetMapping("/settlement//balance")

    @DeleteMapping("/settlement/{settlementId}")
    public ApiResponse deleteSettlement(@PathVariable Long settlementId) {
        settlementService.deleteSettlement(settlementId);
        return makeResponse("Delete Successful");
    }
}
