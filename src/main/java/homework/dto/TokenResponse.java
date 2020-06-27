package homework.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {
    private String token;

    @Builder
    public TokenResponse(String token) {
        this.token = token;
    }
}
