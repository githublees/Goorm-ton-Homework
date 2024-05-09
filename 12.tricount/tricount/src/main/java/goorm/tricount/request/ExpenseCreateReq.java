package goorm.tricount.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ExpenseCreateReq {
    private String title;
    private String amount;
}
