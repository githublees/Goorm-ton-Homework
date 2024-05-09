package goorm.tricount.controller;

import goorm.tricount.request.common.ApiResponse;
import goorm.tricount.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BalanceController extends BaseController {

    private final BalanceService balanceService;

    @GetMapping("/balance/{settlementId}")
    public ApiResponse getBalance(@PathVariable Long settlementId) {
        balanceService.deleteBalance(settlementId);
        return  makeResponse(balanceService.createBalance(settlementId));
    }
}
