package com.example.core.primitives.results;

import com.example.core.exceptions.base.DomainException;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Represents the result of an operation with a payload, indicating whether it was successful or
 * not. Instances of this class contain information about success status and an error message if
 * applicable.
 *
 * @param <T> the type of the payload associated with this result
 */
public class Result<T> extends ActionResult {

    /**
     * The payload associated with this result, if any.
     */
    private final Optional<T> payload;

    private Result(T payload, DomainException error, boolean success) {
        super(success, error);
        this.payload = Optional.ofNullable(payload);
    }

    /**
     * Constructs a new {@code Result} instance representing a successful operation with a payload.
     *
     * @param payload the payload associated with this result
     * @throws IllegalArgumentException if the payload is {@code null}
     */
    public Result(T payload) {
        super(true, null);
        this.payload = Optional.of(Objects.requireNonNull(payload));
    }

    /**
     * Constructs a new {@code Result} instance representing a failed operation with an error.
     *
     * @param error the exception associated with the failed operation
     */
    public Result(DomainException error) {
        super(false, error);
        this.payload = Optional.empty();
    }

    /**
     * Returns the payload associated with this result.
     *
     * @return the payload
     * @throws ResultEmptyException if the operation was not successful or the payload is not set
     */
    public T getOk() throws ResultEmptyException {
        if (!isSuccess()) {
            throw new ResultEmptyException(ErrorCode.DOM_INVALID_STATE,
                "The operation was not successful.");
        }
        if (!payload.isPresent()) {
            throw new ResultEmptyException(ErrorCode.DOM_INVALID_STATE, "The payload was not set.");
        }
        return payload.get();
    }

    /**
     * Returns the exception associated with this result.
     *
     * @return the exception
     * @throws ResultEmptyException if there is no error associated with this result
     */
    @Override
    public DomainException getError() throws ResultEmptyException {
        if (error.isEmpty()) {
            throw new ResultEmptyException(ErrorCode.DOM_INVALID_STATE,
                "Error property for this Result not set.");
        }
        return error.get();
    }

    /**
     * Creates a new {@code Result} instance representing a successful operation with a payload.
     *
     * @param <T> the type of the payload
     * @param payload the payload associated with the successful operation
     * @return a {@code Result} instance with {@code success} set to {@code true} and the specified
     *         payload
     */
    public static <T> Result<T> ok(T payload) {
        return new Result<>(Objects.requireNonNull(payload), null, true);
    }

    /**
     * Creates a new {@code Result} instance representing a failed operation with an error.
     *
     * @param <T> the type of the payload
     * @param error the exception associated with the failed operation
     * @return a {@code Result} instance with {@code success} set to {@code false} and the specified
     *         error
     */
    public static <T> Result<T> fail(DomainException error) {
        return new Result<>(null, Objects.requireNonNull(error), false);
    }
}
