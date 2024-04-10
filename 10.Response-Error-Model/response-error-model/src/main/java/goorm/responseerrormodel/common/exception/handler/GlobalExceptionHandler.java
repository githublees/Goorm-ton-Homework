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
    protected ApiResponse customExceptionHandler(HttpServletResponse response, CustomException e) {

        return new ApiResponse(e.getErrorCode(), e.getMessage(), e.getData());
    }
}
