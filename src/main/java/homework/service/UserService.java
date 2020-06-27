package homework.service;

import homework.datasource.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public boolean isExistUser(long id) {
        return repository.existsById(id);
    }
}
