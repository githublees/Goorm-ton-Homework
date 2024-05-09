package goorm.tricount.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.tricount.domain.Join;
import goorm.tricount.domain.Settlement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SettlementRes {

    private Long settlementNo;
    private String settlementTitle;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserRes> joins = new ArrayList<>();

    public static SettlementRes res(Settlement settlement) {
        SettlementRes settlementRes = new SettlementRes();
        settlementRes.settlementNo = settlement.getId();
        settlementRes.settlementTitle = settlement.getSettlementName();
        settlementRes.joins = settlement.getJoins()
                .stream()
                .map(UserRes::res)
                .collect(Collectors.toList());

        return settlementRes;
    }

    public static List<SettlementRes> res(List<Settlement> settlements) {
        List<SettlementRes> settlementResList = new ArrayList<>();

        for (Settlement settlement : settlements) {
            SettlementRes settlementRes = new SettlementRes();
            settlementRes.settlementNo = settlement.getId();
            settlementRes.settlementTitle = settlement.getSettlementName();
            settlementRes.joins = null;
            settlementResList.add(settlementRes);
        }
        return settlementResList;
    }
}
