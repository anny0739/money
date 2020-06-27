package homework.controller;

import homework.datasource.repository.ChatRoomRepository;
import homework.service.ChatRoomService;
import homework.service.SplitMoneyService;
import homework.service.TokenService;
import homework.service.UserService;
import homework.testsupport.BaseTest;
import homework.testsupport.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(SplitMoneyController.class)
@ContextConfiguration(classes = {TestConfig.class, ChatRoomService.class, UserService.class,
        TokenService.class, SplitMoneyService.class, ChatRoomRepository.class})
public class SplitMoneyControllerWebMvcTest extends BaseTest {

    private static final String API_URL = "/api/v1/split";

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

        final String emptyErrorMessage = "Room Id is not valid";
        assertThat(exception.getMessage(), containsString(emptyErrorMessage));
    }

    @Test
    public void notExistUserId() {
        Exception exception = assertThrows(SecurityException.class, () ->
                mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .header(X_ROOM_ID, "1")));

        final String emptyErrorMessage = "Room Id is not valid";
        assertThat(exception.getMessage(), containsString(emptyErrorMessage));
    }

}