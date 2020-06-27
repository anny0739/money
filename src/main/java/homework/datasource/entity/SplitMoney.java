package homework.datasource.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SplitMoney extends BaseEntity {

    @Builder
    public SplitMoney(long chatRoomId, long userId, String token, int number, int amount, LocalDateTime expiredAt, List<SplitMoneyUser> splitMoneyUsers) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.token = token;
        this.number = number;
        this.amount = amount;
        this.expiredAt = expiredAt;
        this.splitMoneyUsers = splitMoneyUsers;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "split_money_id")
    private long id;

    private long chatRoomId;

    private long userId;

    private String token;

    private int number;

    private int amount;

    private LocalDateTime expiredAt;

    @Setter
    @OneToMany(mappedBy = "splitMoney", cascade = CascadeType.ALL)
    private List<SplitMoneyUser> splitMoneyUsers = new ArrayList<>();
}
