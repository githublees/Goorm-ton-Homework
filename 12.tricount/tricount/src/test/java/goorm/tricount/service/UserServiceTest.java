package goorm.tricount.service;

import goorm.tricount.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;

    @Test
    public void 회원_가입() throws Exception {
        //given
        User user = User.createUser("wlzhf", "1234", "호성");

        //when
        Long savedId = userService.save(user);
        User findUser = userService.getUser(savedId);

        //then
        assertEquals("호성", findUser.getNickname());
        assertEquals("wlzhf", findUser.getUserName());
    }
}