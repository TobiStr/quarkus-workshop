package com.example.core.exceptions;

import java.util.Optional;

public enum ErrorCode {
    // ---- API_ERROR -----------------------------------

    /**
     * API Layer: An unknown exception occurred, while processing the request.
     */
    API_UNKNOWN(100, "An unknown exception occurred, while processing the request."),

    /**
     * API Layer: The request is invalid due to invalid input parameters.
     *
     * @apiNote: Validate the request payload and parameters.
     */
    API_INVALID_INPUT(101, "The request is invalid due to invalid input parameters."),

    /**
     * API Layer: The request is invalid due to missing input parameters.
     *
     * @apiNote: Validate the request payload and parameters.
     */
    API_MISSING_INPUT(102, "The request is invalid due to missing input parameters."),

    /**
     * API Layer: Unauthorized access to the resource.
     *
     * @apiNote: Ensure valid authentication credentials are provided.
     */
    API_UNAUTHORIZED(103, "Unauthorized access to the resource."),

    /**
     * API Layer: Access to the resource is forbidden.
     *
     * @apiNote: Ensure the user has the necessary permissions and roles.
     */
    API_FORBIDDEN(104, "Access to the resource is forbidden."),

    /**
     * API Layer: The request entity is too large.
     *
     * @apiNote: Reduce the size of the request payload (e.g. attached file).
     */
    API_PAYLOAD_TOO_LARGE(105, "The request entity is too large."),

    /**
     * API Layer: Too many requests have been made in a short period.
     *
     * @apiNote: Wait and try the request again later.
     */
    API_TOO_MANY_REQUESTS(106, "Too many requests have been made in a short period."),


    // ---- INFRASTRUCTURE_ERROR --------------------------------

    /**
     * Infrastructure Layer: An unknown exception occurred, while processing an external API.
     */
    INF_UNKNOWN(200, "An unknown exception occurred, while processing an external API."),

    /**
     * Infrastructure Layer: Database connectivity issues.
     */
    INF_DB_CONNECTION(201, "Database connectivity issues."),

    /**
     * Infrastructure Layer: No entry found in database.
     */
    INF_DB_NOTFOUND(202, "No entry found in database."),

    /**
     * Infrastructure Layer: Duplicate entity detected.
     */
    INF_DUPLICATE_ENTITY(203, "Duplicate entity detected."),

    /**
     * Infrastructure Layer: Database query syntax error.
     */
    INF_DB_SYNTAX_ERROR(204, "Database query syntax error."),

    /**
     * Infrastructure Layer: Database timeout occurred.
     */
    INF_DB_TIMEOUT(205, "Database timeout occurred."),

    /**
     * Infrastructure Layer: Insufficient object data for database entry.
     */
    INF_DB_INSUFFICIENT_DATA(206, "Insufficient object data for database entry."),

    /**
     * Infrastructure Layer: Database constraint violation.
     */
    INF_DB_CONSTRAINT_VIOLATION(207, "Database constraint violation."),

    /**
     * Infrastructure Layer: Unauthorized access to the database.
     */
    INF_DB_UNAUTHORIZED_ACCESS(208, "Unauthorized access to the database."),

    /**
     * Infrastructure Layer: Database disk space exhausted.
     */
    INF_DB_DISK_SPACE_EXHAUSTED(209, "Database disk space exhausted."),

    /**
     * Infrastructure Layer: Unknown Database Error.
     */
    INF_DB_UNKNOWN(210, "Unknown database error."),

    /**
     * Infrastructure Layer: External API operation failed.
     */
    INF_API_OPERATION_FAILED(211, "External API operation failed."),

    /**
     * Infrastructure Layer: Internal auth exception.
     */
    INF_UNAUTHORIZED(212, "Internal auth exception."),

    // ---- DOMAIN_ERROR --------------------------------------

    /**
     * Domain/Business Layer: An unknown exception occurred internally.
     */
    DOM_UNKNOWN(300, "An unknown exception occurred internally."),

    /**
     * Domain/Business Layer: Parameter validation failed.
     *
     * @apiNote: Validate the request payload and parameters.
     */
    DOM_INVALID_ARGUMENTS(301, "Parameter validation failed."),

    /**
     * Domain/Business Layer: Data integrity violation.
     *
     * @apiNote: Ensure the data integrity constraints are met.
     */
    DOM_DATA_INTEGRITY_VIOLATION(302, "Data integrity violation."),

    /**
     * Domain/Business Layer: Object has an invalid state.
     *
     * @apiNote: Ensure the state of the concerning object matches the requirements.
     */
    DOM_INVALID_STATE(303, "Object has an invalid state."),

    /**
     * Domain/Business Layer: Object was not found.
     *
     * @apiNote: Ensure that the object you are requesting exists.
     */
    DOM_NOT_FOUND(304, "Object was not found."),

    // ---- OTHER -----------------------------------------------

    /**
     * Other: An unknown exception occurred.
     */
    OTH_UNKNOWN(400, "An unknown exception occurred.");

    private final int errorNumber;

    private final String description;

    private final Family family;

    /**
     * An enumeration representing the class of error code. Family is used here since class is
     * overloaded in Java.
     */
    public enum Family {
        /**
         * {@code 1xx} error codes occuring in the api layer.
         */
        API_ERROR,
        /**
         * {@code 2xx} error codes occuring in the infrastructure layer.
         */
        INFRASTRUCTURE_ERROR,
        /**
         * {@code 3xx} error codes occuring in the business/domain layer.
         */
        DOMAIN_ERROR,
        /**
         * Other, unrecognized error codes.
         */
        OTHER;

        /**
         * Get the response status family for the status code.
         *
         * @param statusCode response status code to get the family for.
         * @return family of the response status code.
         */
        public static Family familyOf(final ErrorCode errorCode) {
            var statusCode = errorCode.getErrorNumber();

            switch (statusCode / 100) {
                case 1:
                    return Family.API_ERROR;
                case 2:
                    return Family.INFRASTRUCTURE_ERROR;
                case 3:
                    return Family.DOMAIN_ERROR;
                default:
                    return Family.OTHER;
            }
        }
    }

    ErrorCode(final int errorNumber, final String description) {
        this.errorNumber = errorNumber;
        this.description = description;
        this.family = Family.familyOf(this);
    }

    /**
     * Get the public error number.
     *
     * @return the public error number.
     */
    public int getErrorNumber() {
        return errorNumber;
    }

    /**
     * Get the public error description.
     *
     * @return the public error description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the error family.
     *
     * @return the the error family.
     */
    public Family getFamily() {
        return family;
    }

    public static Optional<ErrorCode> fromString(final String errorCode) {
        for (ErrorCode e : ErrorCode.values()) {
            if (e.toString().equals(errorCode)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }
}
