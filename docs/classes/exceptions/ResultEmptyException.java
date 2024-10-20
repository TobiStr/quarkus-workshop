package com.example.core.exceptions;

import jakarta.ws.rs.core.Response.Status;
import com.example.core.exceptions.base.DomainException;

public class ResultEmptyException extends DomainException {
    public ResultEmptyException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
