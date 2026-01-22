package sonnh.dev.assetsmanagement.request;

import lombok.Data;

import java.util.Map;

@Data
public class OllamaGenerateRequest {

    private String model;
    private String prompt;
    private boolean stream = false;
    private Map<String, Object> options;
}

