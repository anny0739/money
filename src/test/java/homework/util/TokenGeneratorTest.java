package homework.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasLength;

class TokenGeneratorTest {

    @Test
    void generate() {
        Assert.assertThat(TokenGenerator.generate(), hasLength(3));
    }
}