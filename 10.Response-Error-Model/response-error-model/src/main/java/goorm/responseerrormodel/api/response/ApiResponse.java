package goorm.responseerrormodel.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goorm.responseerrormodel.common.exception.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public class ApiResponse<T> {

    private Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Metadata metadata;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ApiResponse(List<T> results) {
        this.status = new Status(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.metadata = new Metadata(results.size());
        this.result = results;
    }

    public ApiResponse(T result) {
        this.status = new Status(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.metadata = new Metadata(1);
        this.result = List.of(result);
    }

    public ApiResponse(int code, String message) {
        this.status = new Status(code, message);
    }

    public ApiResponse(int code, String message, Object data) {
        this.status = new Status(code, message);
        this.data = data;
    }
}
