package homework.controller;

import homework.testsupport.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class SplitMoneyControllerWebMvcTest extends BaseTest {

    private static final String API_URL = "/api/v1/split-money";

    @Test
    public void ifHeaderIsEmpty() {
        Exception exception = assertThrows(SecurityException.class, () ->
                mockMvc.perform(MockMvcRequestBuilders.post(API_URL)));

        final String emptyErrorMessage = "Empty Header is not allowed!";
        assertEquals(emptyErrorMessage, exception.getMessage());
    }

    @Test
    public void notExistRoomId() {
        Exception exception = assertThrows(SecurityException.class, () ->
                mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .header(X_ROOM_ID, "1")));

        final String emptyErrorMessage = "Empty Header is not allowed!";
        assertThat(exception.getMessage(), containsString(emptyErrorMessage));
    }

    @Test
    public void notExistUserId() {
        Exception exception = assertThrows(SecurityException.class, () ->
                mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .header(X_ROOM_ID, "1")));

        final String emptyErrorMessage = "Empty Header is not allowed!";
        assertThat(exception.getMessage(), containsString(emptyErrorMessage));
    }

}