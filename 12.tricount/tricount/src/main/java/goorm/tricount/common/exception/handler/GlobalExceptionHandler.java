package goorm.tricount.common.exception.handler;

import goorm.tricount.common.exception.CustomException;
import goorm.tricount.request.common.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected <T> ApiResponse<T> customExceptionHandler(HttpServletResponse response, CustomException e) {

        response.setStatus(e.getErrorCode().getHttpStatus().value());

        return new ApiResponse(e.getErrorCode().getCode(), e.getMessage());
    }
}
