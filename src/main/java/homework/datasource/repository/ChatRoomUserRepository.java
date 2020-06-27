package homework.datasource.repository;

import homework.datasource.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    boolean existsByChatRoomIdAndUserId(long roomId, long userId);
}
