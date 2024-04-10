package goorm.responseerrormodel.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.responseerrormodel.common.exception.InputRestriction;
import lombok.Getter;

import java.util.List;

@Getter
public class ApiResponse<T> {

    private Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
    private Metadata metadata;

    private Object result;

    public ApiResponse(List<T> result) {
        this.status = new Status(200, "OK");
        this.metadata = new Metadata(result.size());
        this.result = result;
    }

    public ApiResponse(T result) {
        this.status = new Status(200, "OK");
        this.metadata = new Metadata(1);
        this.result = result;
    }

    public ApiResponse(int code, String message, Object data) {
        this.status = new Status(code, message);
        this.result = data;
    }
}
