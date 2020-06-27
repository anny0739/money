package homework.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;

public class MoneyRandomSplitor {
    public static int[] splitRandomMoney(int amount, int number) {
        if (!amountGreaterThanEqualsNumber(amount, number)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "amount must be greater than number");
        }

        int minRandom = amount / number;
        int money[] = new int[number];

        int sumSplitAmount = 0;
        for (int i = 0; i < number && sumSplitAmount < amount; i++) {
            money[i] = new Random().nextInt(minRandom + 1);
            sumSplitAmount += money[i];
        }

        if (amount - sumSplitAmount > 0) {
            money[new Random().nextInt(number)] += amount - sumSplitAmount;
        }

        return money;
    }

    private static boolean amountGreaterThanEqualsNumber(int amount, int number) {
        return amount >= number;
    }
}
