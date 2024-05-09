package goorm.tricount.response;

import goorm.tricount.domain.Join;
import goorm.tricount.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRes {

    private Long userNo;
    private String userName;

    public static UserRes res(Join join) {
        UserRes userRes = new UserRes();
        userRes.userNo = join.getUser().getId();
        userRes.userName = join.getUser().getUserName();
        return userRes;
    }
}
