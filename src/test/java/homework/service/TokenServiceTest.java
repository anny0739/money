package homework.service;

import homework.testsupport.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TokenServiceTest extends BaseTest {

    @Autowired
    private TokenService tokenService;

    @Test
    public void successGetToken() {
        LocalDateTime now = LocalDateTime.now();
        assertEquals(3, tokenService.getToken(now).length());
    }
}