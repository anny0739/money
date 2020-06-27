package homework.datasource.repository;

import homework.datasource.entity.SplitMoney;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SplitMoneyRepository extends JpaRepository<SplitMoney, Long> {
    boolean existsByTokenAndExpiredAtAfter(String token, LocalDateTime now);

    Optional<SplitMoney> findByChatRoomIdAndToken(long chatRoomId, String token);
}
