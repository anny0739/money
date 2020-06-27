package homework.datasource.repository;

import homework.datasource.entity.SplitMoney;
import homework.datasource.entity.SplitMoneyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SplitMoneyUserRepository extends JpaRepository<SplitMoneyUser, Long> {
    boolean existsBySplitMoneyAndUserId(SplitMoney splitMoney, long userId);

    Optional<SplitMoneyUser> findTopBySplitMoneyAndUserIdIsNull(SplitMoney splitMoney);
}
