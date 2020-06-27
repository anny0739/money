package homework.service;

import homework.datasource.entity.SplitMoney;
import homework.testsupport.BaseTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class SplitMoneyServiceTest extends BaseTest {

    private static final int AMOUNT = 2;
    private static final String TOKEN = "tes";
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Autowired
    private SplitMoneyService service;

    @Test
    public void saveSplitMoney() {
        service.save(givenSplitMoney());

        SplitMoney splitMoney = service.getSplitMoney(1, TOKEN);
        Assert.assertEquals(AMOUNT, splitMoney.getAmount());
    }

    private SplitMoney givenSplitMoney() {
        return SplitMoney.builder()
                .amount(AMOUNT)
                .chatRoomId(1)
                .expiredAt(NOW.plusMinutes(10))
                .number(2)
                .token(TOKEN)
                .userId(1)
                .build();
    }

}