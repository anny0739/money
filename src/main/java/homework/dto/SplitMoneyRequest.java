package homework.dto;

import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
public class SplitMoneyRequest {
    @Min(value = 2)
    private int amount;
    @Min(value = 2)
    private int number;
}
