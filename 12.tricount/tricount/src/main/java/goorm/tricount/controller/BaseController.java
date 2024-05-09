package goorm.tricount.controller;

import goorm.tricount.request.common.ApiResponse;

import java.util.List;

abstract public class BaseController {
    public <T> ApiResponse<T> makeResponse(List<T> result) {
        return new ApiResponse<>(result);
    }

    public <T> ApiResponse<T> makeResponse(T result) {
        return new ApiResponse<>(result);
    }
}
