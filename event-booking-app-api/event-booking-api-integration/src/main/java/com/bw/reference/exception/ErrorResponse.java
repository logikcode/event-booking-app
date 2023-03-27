package com.bw.reference.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

/**
 * @author Neme Iloeje <niloeje@byteworks.com.ng>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ErrorResponse extends RuntimeException {
    private String messageKey;
    private Object[] args;
    private int code;

    public ErrorResponse(int code, String messageKey, @Nullable Object[] args) {
        this.messageKey = messageKey;
        this.args = args;
        this.code = code;
    }

}
