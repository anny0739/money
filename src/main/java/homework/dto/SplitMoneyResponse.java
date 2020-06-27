package homework.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SplitMoneyResponse {
    private LocalDateTime createdAt;
    private int amount;
    private int receivedAmount;

    private List<Money> moneys;

    @Getter
    public static class Money {
        private int amount;
        private Long userId;

        @Builder
        public Money(int amount, Long userId) {
            this.amount = amount;
            this.userId = userId;
        }
    }

    @Builder
    public SplitMoneyResponse(LocalDateTime createdAt, int amount, int receivedAmount, List<Money> moneys) {
        this.createdAt = createdAt;
        this.amount = amount;
        this.receivedAmount = receivedAmount;
        this.moneys = moneys;
    }

}
