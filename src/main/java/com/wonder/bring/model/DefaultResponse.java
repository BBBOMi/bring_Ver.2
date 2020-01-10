package com.wonder.bring.model;

import com.wonder.bring.utils.Message;
import com.wonder.bring.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class DefaultResponse<T> {

    private int status;

    private String message;

    private T data;

    public DefaultResponse(final int status, final String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public static<T> DefaultResponse<T> res(final int status, final String message) {
        return res(status, message, null);
    }

    public static<T> DefaultResponse<T> res(final int status, final String message, final T t) {
        return DefaultResponse.<T>builder()
                .data(t)
                .status(status)
                .message(message)
                .build();
    }

    public static final DefaultResponse FAIL_DEFAULT_RESPONSE = new DefaultResponse(Status.INTERNAL_SERVER_ERROR, Message.INTERNAL_SERVER_ERROR);
    public static final DefaultResponse BAD_REQUEST_RESPONSE = new DefaultResponse(Status.BAD_REQUEST, Message.NOT_GET_MY_POINT);
}
