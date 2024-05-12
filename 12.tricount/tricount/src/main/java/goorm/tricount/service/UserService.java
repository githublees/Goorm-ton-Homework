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

    @Transactional
    public Long save(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public User login(String userId, String password) {
        return userRepository.findByUserId(userId, password)
                .orElseThrow(() -> new RuntimeException("User info is not found"));
    }

    public User getUser(Long userId) {
        return userRepository.findOne(userId);
    }
}
