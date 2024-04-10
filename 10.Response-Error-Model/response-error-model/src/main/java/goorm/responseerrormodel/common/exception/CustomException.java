package goorm.responseerrormodel.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int errorCode;
    private final String message;
    private final Object data;

    public CustomException(ErrorCode errorCode, Object data) {
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }
}
