package goorm.tricount.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateReq {
    private String userId;
    private String password;
    private String nickname;
}
