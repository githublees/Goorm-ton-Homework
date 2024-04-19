package goorm.notice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
public class ApiResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<T> results;

    public ApiResponse(List<T> results) {
        this.results = results;
    }

    public ApiResponse(T result) {
        this.results = List.of(result);
    }

}
