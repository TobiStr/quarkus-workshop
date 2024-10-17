package com.example.core.primitives.results;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import com.example.core.exceptions.base.DomainException;

/**
 * Represents the result of an operation, indicating whether it was successful or not. Instances of
 * this class contain information about success status and an error message if applicable.
 */
public class ActionResult {
    /**
     * The exception associated with this result, if any.
     */
    protected final Optional<DomainException> error;

    /**
     * Indicates whether the operation was successful.
     */
    private final boolean success;

    protected ActionResult(boolean success, DomainException error) {
        this.success = success;
        this.error = error == null ? Optional.empty() : Optional.of(error);
    }

    /**
     * Returns whether the operation was successful.
     *
     * @return {@code true} if the operation was successful, {@code false} otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the error message associated with this result.
     *
     * @return the error message, or an empty string if there is no error
     */
    public String getMessage() {
        return error.isPresent() ? error.get().getMessage() : "";
    }

    /**
     * Returns the exception associated with this result.
     *
     * @return the exception
     * @throws ResultEmptyException if there is no error associated with this result
     */
    public DomainException getError() throws ResultEmptyException {
        if (error.isEmpty()) {
            throw new ResultEmptyException(ErrorCode.DOM_INVALID_STATE,
                "Error property for this Result not set.");
        }
        return error.get();
    }

    /**
     * Creates a new {@code Result} instance representing a successful operation.
     *
     * @return a {@code Result} instance with {@code success} set to {@code true} and no error
     */
    public static ActionResult ok() {
        return new ActionResult(true, null);
    }

    /**
     * Creates a new {@code Result} instance representing a failed operation.
     *
     * @param error the exception associated with the failed operation
     * @return a {@code Result} instance with {@code success} set to {@code false} and the specified
     *         error
     */
    public static ActionResult error(DomainException error) {
        return new ActionResult(false, error);
    }

    public ActionResult toActionResult() {
        return this;
    }

    public <U> Result<U> toFailedResult() {
        return new Result<>(error.get());
    }
}
