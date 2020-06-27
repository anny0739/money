package homework.controller;

import homework.datasource.entity.SplitMoney;
import homework.datasource.entity.SplitMoneyUser;
import homework.datasource.repository.SplitMoneyRepository;
import homework.datasource.repository.SplitMoneyUserRepository;
import homework.testsupport.BaseTest;
import homework.testsupport.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
public class SplitMoneyControllerTest extends BaseTest {

    private static final String API_URL = "/api/v1/split-money";

    @Autowired
    private SplitMoneyRepository splitMoneyRepository;

    @Autowired
    private SplitMoneyUserRepository splitMoneyUserRepository;

    @Test
    public void notExistUserId() {
        Exception exception = assertThrows(SecurityException.class, () ->
                mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                        .header(X_ROOM_ID, "1")));

        final String emptyErrorMessage = "Empty Header is not allowed";
        assertThat(exception.getMessage(), containsString(emptyErrorMessage));
    }

    @Test
    public void invalidRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":0, \"number\":1}"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("message").value("Method Argument Not Valid"));
    }

    @Test
    public void successSplitMoney() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":2, \"number\":2}"))
                .andExpect(status().isOk());
    }

    @Test
    public void tokenIsNotExist() throws Exception {
        final String token = "tt-";
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/receive")
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"token\":\"%s\"}", token)))
                .andExpect(status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @Test
    public void hostCantReceiveMoney() throws Exception {
        givenSplitMoney();

        final String token = "ttt";
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/receive")
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"token\":\"%s\"}", token)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andDo(print())
                .andExpect(jsonPath("$.message").value("Host Can't Receive Money"));
    }

    @Test
    public void isNotChatRoomUser() throws Exception {
        givenSplitMoney();

        final String token = "ttt";
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/receive")
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"token\":\"%s\"}", token)))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("is not chat room user"));
    }

    @Test
    public void alreadyReceivedMoney() throws Exception {
        givenSplitMoney();

        final String token = "ttt";
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/receive")
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"token\":\"%s\"}", token)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("already received money"));
    }

    @Test
    public void successReceivedMoney() throws Exception {
        givenSplitMoney();

        final String token = "ttt";
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/receive")
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"token\":\"%s\"}", token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", greaterThan(0)));

    }

    @Test
    public void expiredSplitMoney() throws Exception {
        givenExpiredSplitMoney();

        final String token = "ttt";
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/receive")
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"token\":\"%s\"}", token)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("token is expired"));

    }

    @Test
    public void onlyHostGetSplitMoneyInfo() throws Exception {
        givenSplitMoney();

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL)
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "2")
                .param("token", "ttt"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Only Host Get Split Money Info"));
    }

    @Test
    public void ExceedInquiryPeriodPassed() throws Exception {
        givenSplitMoney();

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL)
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "1")
                .param("token", "yv-"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Exceed Inquiry Period"));
    }

    @Test
    public void successGet() throws Exception {
        givenSplitMoney();

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL)
                .header(X_ROOM_ID, "1")
                .header(X_USER_ID, "1")
                .param("token", "ttt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created_at").isNotEmpty())
                .andExpect(jsonPath("$.received_amount").value(1))
                .andExpect(jsonPath("$.amount").value(3))
                .andExpect(jsonPath("$.moneys").value(hasSize(3)))
                .andExpect(jsonPath("$.moneys[2].user_id").isNotEmpty())
        ;
    }

    private void givenSplitMoney() {
        SplitMoney splitMoney = SplitMoney.builder()
                .amount(3)
                .chatRoomId(1)
                .expiredAt(LocalDateTime.now().plusMinutes(10))
                .number(2)
                .token("ttt")
                .userId(1)
                .build();

        List<SplitMoneyUser> users = IntStream.range(0, 2)
                .mapToObj(r -> SplitMoneyUser.builder().amount(1).splitMoney(splitMoney).build())
                .collect(Collectors.toList());
        SplitMoneyUser user = SplitMoneyUser.builder().amount(1).splitMoney(splitMoney).build();
        user.setUserId(3l);
        users.add(user);
        splitMoney.setSplitMoneyUsers(users);

        splitMoneyRepository.save(splitMoney);
    }

    private void givenExpiredSplitMoney() {
        SplitMoney splitMoney = SplitMoney.builder()
                .amount(3)
                .chatRoomId(1)
                .expiredAt(LocalDateTime.now().minusMinutes(10))
                .number(2)
                .token("ttt")
                .userId(1)
                .build();
        splitMoneyRepository.save(splitMoney);
    }

}