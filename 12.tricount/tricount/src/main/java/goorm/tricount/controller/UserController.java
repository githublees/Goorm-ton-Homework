package goorm.tricount.controller;

import goorm.tricount.common.exception.CustomException;
import goorm.tricount.common.exception.ErrorCode;
import goorm.tricount.domain.User;
import goorm.tricount.request.UserCreateReq;
import goorm.tricount.request.UserLoginReq;
import goorm.tricount.request.common.ApiResponse;
import goorm.tricount.request.common.SessionConst;
import goorm.tricount.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("/users")
    public ApiResponse createUser(@RequestBody UserCreateReq userCreateReq) {
        return  makeResponse(userService.save(
                    User.createUser(
                        userCreateReq.getUserId(),
                        userCreateReq.getPassword(),
                        userCreateReq.getNickname())));
    }

    @PostMapping("/login")
    public ApiResponse login(
            @RequestBody UserLoginReq userLoginReq,
            HttpServletRequest request
    ) {
        User loginUser = userService.login(userLoginReq.getUserId(), userLoginReq.getPassword());

        if (loginUser == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        return makeResponse("Login successful");
    }

    @GetMapping("/logout")
    public ApiResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "로그인한 상태가 아닙니다.");
        }

        session.invalidate();

        return makeResponse("Logout successful");
    }
}
