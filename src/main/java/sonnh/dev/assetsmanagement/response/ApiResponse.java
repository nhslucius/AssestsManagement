package sonnh.dev.assetsmanagement.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import sonnh.dev.assetsmanagement.constant.ErrorCode;
import sonnh.dev.assetsmanagement.filter.TraceContext;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Standard API response")
public class ApiResponse<T> {

    private String code;
    private String message;
    private String traceId;
    private LocalDateTime timestamp;
    private T data;
    private Object errors;

    /* =========================
     * FACTORY
     * ========================= */

    private static <T> ApiResponse<T> base() {
        return ApiResponse.<T>builder()
                .traceId(TraceContext.getTraceId())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /* =========================
     * SUCCESS
     * ========================= */

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> res = base();
        res.code = ErrorCode.SUCCESS.getCode();
        res.message = ErrorCode.SUCCESS.getDefaultMessage();
        res.data = data;
        return res;
    }

    /* =========================
     * ERROR
     * ========================= */

    public static ApiResponse<Void> error(ErrorCode errorCode) {
        ApiResponse<Void> res = base();
        res.code = errorCode.getCode();
        res.message = errorCode.getDefaultMessage();
        return res;
    }

    public static ApiResponse<Void> error(
            ErrorCode errorCode,
            Object errors
    ) {
        ApiResponse<Void> res = error(errorCode);
        res.errors = errors;
        return res;
    }
}
