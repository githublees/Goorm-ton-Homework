package goorm.responseerrormodel.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SERVER_ERROR(5000, "grade 는 6 이상을 입력 할 수 없습니다.");

    private final int code;
    private final String message;
}
