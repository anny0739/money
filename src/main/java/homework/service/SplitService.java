package homework.service;

import homework.datasource.entity.SplitMoney;
import homework.datasource.entity.SplitMoneyUser;
import homework.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SplitService {

    private final ChatRoomService chatRoomService;
    private final TokenService tokenService;
    private final SplitMoneyService splitMoneyService;

    public TokenResponse splitMoney(long roomId, long userId, SplitMoneyRequest request) {
        final LocalDateTime now = LocalDateTime.now();
        SplitMoney splitMoney = SplitMoney.builder()
                .chatRoomId(roomId)
                .userId(userId)
                .amount(request.getAmount())
                .number(request.getNumber())
                .token(tokenService.getToken(now))
                .expiredAt(now.plusMinutes(10))
                .build();

        splitMoneyService.save(splitMoney);

        return TokenResponse.builder().token(splitMoney.getToken()).build();
    }

    public RecevieMoneyResponse receiveMoney(long roomId, long userId, ReceiveMoneyRequest request) {
        validReceiveRequest(roomId, userId, request);

        SplitMoneyUser splitMoneyUser = splitMoneyService.getReceiveMoney(roomId, request.getToken());
        splitMoneyUser.setUserId(userId);

        splitMoneyService.save(splitMoneyUser);

        return RecevieMoneyResponse.builder().amount(splitMoneyUser.getAmount()).build();
    }

    private void validReceiveRequest(long roomId, long userId, ReceiveMoneyRequest request) {
        SplitMoney splitMoney = splitMoneyService.getSplitMoney(roomId, request.getToken());
        if (splitMoney.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "token is expired");
        }

        if (splitMoney.getUserId() == userId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Host Can't Receive Money");
        }

        if (!chatRoomService.isChatRoomUser(roomId, userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "is not chat room user");
        }

        if (splitMoneyService.receivedMoney(roomId, request.getToken(), userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "already received money");
        }
    }

    public SplitMoneyResponse getSplitMoney(long roomId, long userId, String token) {
        final LocalDateTime now = LocalDateTime.now();

        SplitMoney splitMoney = splitMoneyService.getSplitMoney(roomId, token);

        validGetMoney(splitMoney, userId, now);

        List<SplitMoneyUser> splitMoneyUsers = splitMoney.getSplitMoneyUsers();
        int receivedAmount = splitMoneyService.getReceivedAmount(splitMoneyUsers);

        List<SplitMoneyResponse.Money> splitMoneyInfos = splitMoneyUsers.stream()
                .map(smu -> SplitMoneyResponse.Money.builder().amount(smu.getAmount()).userId(smu.getUserId()).build())
                .collect(Collectors.toList());

        return SplitMoneyResponse.builder()
                .amount(splitMoney.getAmount())
                .createdAt(splitMoney.getCreatedAt())
                .moneys(splitMoneyInfos)
                .receivedAmount(receivedAmount)
                .build();
    }

    private void validGetMoney(SplitMoney splitMoney, long userId, LocalDateTime now) {
        if (splitMoney.getUserId() != userId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only Host Get Split Money Info");
        }

        final int availableInquiryDays = 7;
        if (splitMoney.getCreatedAt().plusDays(availableInquiryDays).isBefore(now)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exceed Inquiry Period");
        }
    }

}
