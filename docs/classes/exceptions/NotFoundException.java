package com.example.core.exceptions;

import jakarta.ws.rs.core.Response.Status;
import com.example.core.exceptions.base.DomainException;

public class NotFoundException extends DomainException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
