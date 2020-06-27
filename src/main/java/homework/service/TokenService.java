package homework.service;

import homework.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final SplitMoneyService splitMoneyService;

    public String getToken(LocalDateTime now) {
        String token = TokenGenerator.generate();

        while (splitMoneyService.isExistToken(token, now)) {
            token = TokenGenerator.generate();
        }

        return token;
    }


}
