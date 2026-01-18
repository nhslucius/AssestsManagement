package sonnh.dev.assetsmanagement.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /* =========================
     * COMMON
     * ========================= */
    SUCCESS("SUCCESS", "Success"),
    INTERNAL_ERROR("INTERNAL_ERROR", "Internal server error"),
    INVALID_REQUEST("INVALID_REQUEST", "Invalid request"),

    /* =========================
     * VALIDATION
     * ========================= */
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation error"),
    FIELD_REQUIRED("FIELD_REQUIRED", "Field is required"),
    FIELD_INVALID("FIELD_INVALID", "Field is invalid"),

    /* =========================
     * BUSINESS
     * ========================= */
    DATA_NOT_FOUND("DATA_NOT_FOUND", "Data not found"),
    DATA_ALREADY_EXISTS("DATA_ALREADY_EXISTS", "Data already exists"),
    OPERATION_NOT_ALLOWED("OPERATION_NOT_ALLOWED", "Operation not allowed");

    private final String code;
    private final String defaultMessage;

    ErrorCode(String code, String defaultMessage) {
        this.code = code;
        this.defaultMessage = defaultMessage;
    }
}
