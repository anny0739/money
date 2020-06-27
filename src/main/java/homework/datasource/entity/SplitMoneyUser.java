package homework.datasource.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SplitMoneyUser extends BaseEntity {
    @Id
    @Column(name = "split_money_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "split_money_id", nullable = false)
    private SplitMoney splitMoney;

    @Setter
    private Long userId;

    private int amount;

    @Builder
    public SplitMoneyUser(SplitMoney splitMoney, int amount) {
        this.splitMoney = splitMoney;
        this.amount = amount;
    }

}
