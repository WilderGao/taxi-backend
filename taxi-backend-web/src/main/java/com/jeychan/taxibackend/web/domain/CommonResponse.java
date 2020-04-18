package com.jeychan.taxibackend.web.domain;

import com.jeychan.taxibackend.common.enums.BizErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author 杨世谨
 */
@Data
public class CommonResponse<T> {

    /**
     * Response status code, @see MessageCode
     */
    private int code;

    /**
     * Error message
     */
    private String msg;

    /**
     * Return data object
     */
    private T data;

    CommonResponse() {
        this(HttpStatus.OK.value(), null, null);
    }

    CommonResponse(T data) {
        this(HttpStatus.OK.value(), null, data);
    }

    public CommonResponse(HttpStatus status) {
        this(status.value(), null, null);
    }

    public CommonResponse(HttpStatus status, String message) {
        this(status.value(), message, null);
    }

    public CommonResponse(HttpStatus status, T data) {
        this(status.value(), null, data);
    }

    public CommonResponse(HttpStatus status, String message, T data) {
        this(status.value(), message, data);
    }

    public static CommonResponse emptySuccess() {
        return new CommonResponse();
    }
    public static <T>CommonResponse<T> success(T data) {
        return new CommonResponse(data);
    }

    public static CommonResponse getByBizError(BizErrorCode errorCode) {
        return new CommonResponse(errorCode.getCode(), errorCode.getDesc(), null);
    }

    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }

    public CommonResponse(int code, String message) {
        this.code = code;
        this.msg = message;
    }
}
