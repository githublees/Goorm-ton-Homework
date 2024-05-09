package goorm.tricount.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Join> joins = new ArrayList<>();

    private String userName;

    private String password;

    private String nickname;

    //== 생성 메서드 ==//
    public static User createUser(String userName, String password, String nickname) {
        User user = new User();
        user.userName = userName;
        user.password = password;
        user.nickname = nickname;
        return user;
    }
}
