package sonnh.dev.assetsmanagement.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ApiErrorResponse {

    private String code;
    private String message;
    private Map<String, String> fieldErrors;
}