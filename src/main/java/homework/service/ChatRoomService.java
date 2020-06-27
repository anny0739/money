package homework.service;

import homework.datasource.repository.ChatRoomRepository;
import homework.datasource.repository.ChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository repository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    public boolean isExistRoom(long id) {
        return repository.existsById(id);
    }

    public boolean isChatRoomUser(long roomId, long userId) {
        return chatRoomUserRepository.existsByChatRoomIdAndUserId(roomId, userId);
    }
}
