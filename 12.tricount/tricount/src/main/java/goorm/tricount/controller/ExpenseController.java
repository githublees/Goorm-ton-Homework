package goorm.tricount.controller;

import goorm.tricount.request.ExpenseCreateReq;
import goorm.tricount.request.common.ApiResponse;
import goorm.tricount.response.ExpenseRes;
import goorm.tricount.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseController extends BaseController {

    private final ExpenseService expenseService;

    @GetMapping("/expenses/{settlementId}")
    public ApiResponse getExpenseList(@PathVariable Long settlementId) {
        return  makeResponse(expenseService.getExpenseList(settlementId));
    }

    @PostMapping("/expense/{userId}/{settlementId}")
    public ApiResponse createExpense(
            @PathVariable Long userId,
            @PathVariable Long settlementId,
            @RequestBody ExpenseCreateReq expenseCreateReq
    ) {
        return  makeResponse(expenseService.addExpense(
                    settlementId,
                    userId,
                    expenseCreateReq.getTitle(),
                    new BigDecimal(expenseCreateReq.getAmount())));
    }

    @DeleteMapping("/expense/{expenseId}")
    public ApiResponse deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return makeResponse("Delete Successful");

    }
}
