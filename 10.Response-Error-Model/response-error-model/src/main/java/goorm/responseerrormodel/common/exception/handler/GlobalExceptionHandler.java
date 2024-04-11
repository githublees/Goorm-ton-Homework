package goorm.responseerrormodel.common.exception.handler;

import goorm.responseerrormodel.api.response.ApiResponse;
import goorm.responseerrormodel.common.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected <T> ApiResponse<T> customExceptionHandler(HttpServletResponse response, CustomException e) {

        response.setStatus(e.getErrorCode().getHttpStatus().value());

        return new ApiResponse(e.getErrorCode().getCode(), e.getMessage(), e.getData());
    }
}
