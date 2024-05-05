package goorm.tricount.service;

import goorm.tricount.domain.User;
import goorm.tricount.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long save(User user) {
        userRepository.save(user);
        return user.getId();
    }
}
