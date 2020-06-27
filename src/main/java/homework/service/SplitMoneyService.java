package homework.service;

import homework.datasource.entity.SplitMoney;
import homework.datasource.entity.SplitMoneyUser;
import homework.datasource.repository.SplitMoneyRepository;
import homework.datasource.repository.SplitMoneyUserRepository;
import homework.util.MoneyRandomSplitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SplitMoneyService {
    private final SplitMoneyRepository repository;
    private final SplitMoneyUserRepository splitMoneyUserRepository;

    @Transactional
    public void save(SplitMoney splitMoney) {
        List<SplitMoneyUser> moneyUsers =
                Arrays.stream(MoneyRandomSplitor.splitRandomMoney(splitMoney.getAmount(), splitMoney.getNumber()))
                        .mapToObj(a -> SplitMoneyUser.builder()
                                .splitMoney(splitMoney)
                                .amount(a).build())
                        .collect(Collectors.toList());

        splitMoney.setSplitMoneyUsers(moneyUsers);
        repository.save(splitMoney);
    }

    @Transactional
    public void save(SplitMoneyUser splitMoneyUser) {
        splitMoneyUserRepository.save(splitMoneyUser);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public boolean isExistToken(String token, LocalDateTime now) {
        return repository.existsByTokenAndExpiredAtAfter(token, now);
    }

    public SplitMoney getSplitMoney(long chatRoomId, String token) {
        return repository.findByChatRoomIdAndToken(chatRoomId, token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));
    }

    public int getReceivedAmount(List<SplitMoneyUser> splitMoneyUsers) {
        return splitMoneyUsers.stream()
                .filter(smu -> smu.getUserId() != null)
                .mapToInt(SplitMoneyUser::getAmount).sum();
    }

    public boolean receivedMoney(long roomId, String token, long userId) {
        SplitMoney splitMoney = repository.findByChatRoomIdAndToken(roomId, token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));

        return splitMoneyUserRepository.existsBySplitMoneyAndUserId(splitMoney, userId);
    }

    public SplitMoneyUser getReceiveMoney(long roomId, String token) {
        SplitMoney splitMoney = repository.findByChatRoomIdAndToken(roomId, token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));

        return splitMoneyUserRepository.findTopBySplitMoneyAndUserIdIsNull(splitMoney)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));
    }

}
