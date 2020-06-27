package homework.dto;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class ReceiveMoneyRequest {
    @Size(min = 3, max = 3)
    private String token;
}
