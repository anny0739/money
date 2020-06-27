package homework.util;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyRandomSplitorTest {

    @Test
    public void successSplitMoney() {
        final int amount = 500, number = 5;

        int sum = 0;
        for (int a : MoneyRandomSplitor.splitRandomMoney(amount, number)) {
            sum += a;
        }

        Assert.assertEquals(amount, sum);
    }

    @DisplayName("뿌릴 금액이 뿌릴 인원 보다 작을 순 없다.")
    @Test
    public void amountLessThanNumber() {
        final int amount = 4, number = 5;

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            MoneyRandomSplitor.splitRandomMoney(amount, number);
        });

        final String errorMessage = "amount must be greater than number";
        assertThat(exception.getMessage(), containsString(errorMessage));
    }

    @DisplayName("뿌릴 금액이 뿌릴 인원 보다 같거나 클 수 있다.")
    @Test
    public void amountEqualsNumber() {
        final int amount = 5, number = 5;

        int sum = 0;
        for (int a : MoneyRandomSplitor.splitRandomMoney(amount, number)) {
            sum += a;
        }

        assertEquals(amount, sum);
    }

}