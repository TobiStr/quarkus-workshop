package com.example.core.exceptions.base;

import java.util.UUID;
import java.util.Date;
import java.util.Arrays;
import com.example.core.exceptions.ErrorCode;
import jakarta.ws.rs.core.Response.Status;

/**
 * Base class for domain exceptions. Exceptions inherited from this type may be used accross
 * boundaries (infrastructure, business, api).
 *
 * @apiNote Inherit to Exceptions, that are annotated with {@link ResponseMappable}. These can be
 *          transformed to HttpErrorResponses
 */
public abstract class DomainException extends RuntimeException implements Cloneable {
    private final ErrorCode errorCode;

    private final String errorId;

    private final Date errorDate;

    protected DomainException(ErrorCode errorCode, String message) {
        super(message);

        this.errorCode = errorCode;
        var randomUuidParts = UUID.randomUUID().toString().split("-");
        errorId = randomUuidParts[randomUuidParts.length - 1];
        errorDate = new Date();
    }

    /**
     * Gets the error code.
     *
     * @return the error code.
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Gets the randomly generated error id to identify a specific event.
     *
     * @return the randomly generated error id to identify a specific event.
     */
    public String getErrorId() {
        return errorId;
    }

    /**
     * Gets the occurence date of the error.
     *
     * @return the occurence date of the error.
     */
    public Date getErrorDate() {
        return errorDate;
    }
}
