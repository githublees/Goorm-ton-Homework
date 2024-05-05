package goorm.tricount.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "joins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Join {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SETTLEMENT_ID")
    private Settlement settlement;

    private LocalDateTime joinDate;

    @Enumerated(EnumType.STRING)
    private JoinStatus joinStatus;

    //== 연관관계 메서드 ==//
    public void addUser(User user) {
        this.user = user;
        user.getJoins().add(this);
    }

    public void addSettlement(Settlement settlement) {
        this.settlement = settlement;
        settlement.getJoins().add(this);
    }

    //== 생성 메서드 ==//
    public static Join join(User user, Settlement settlement) {
        Join join = new Join();
        join.addUser(user);
        join.addSettlement(settlement);
        join.joinStatus = JoinStatus.ATTEND;
        join.joinDate = LocalDateTime.now();
        return join;
    }

    //== 비즈니스 로직 ==//
    /**
     * 모임 탈퇴
     */
    public void leave() {
        this.joinStatus = JoinStatus.LEAVE;
    }
}
