package goorm.tricount.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getJoins(), user.getJoins()) && Objects.equals(getUserName(), user.getUserName()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getNickname(), user.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getJoins(), getUserName(), getPassword(), getNickname());
    }
}
