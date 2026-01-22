package sonnh.dev.assetsmanagement.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sonnh.dev.assetsmanagement.request.OllamaGenerateRequest;
import sonnh.dev.assetsmanagement.response.OllamaGenerateResponse;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OllamaClientAdapter implements AiClient {

    private final RestTemplate restTemplate;

    @Value("${ollama.base-url}")
    private String baseUrl;

    @Value("${ollama.model}")
    private String model;

    @Override
    public String generate(String prompt) {

        OllamaGenerateRequest request = new OllamaGenerateRequest();
        request.setModel(model);
        request.setPrompt(buildSystemPrompt(prompt));
        request.setStream(false);

        Map<String, Object> options = new HashMap<>();
        options.put("temperature", 0.15);
        options.put("top_p", 0.9);
        options.put("num_ctx", 256000);
        options.put("num_predict", 2048);

        request.setOptions(options);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OllamaGenerateRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<OllamaGenerateResponse> response =
                restTemplate.postForEntity(
                        baseUrl + "/api/generate",
                        entity,
                        OllamaGenerateResponse.class
                );

        if (response.getBody() == null) {
            throw new RuntimeException("Empty response from Ollama");
        }

        return response.getBody().getResponse();
    }

    private String buildSystemPrompt(String userPrompt) {
        return """
SYSTEM:
You are a high-reasoning analytical AI.
Use deep internal reasoning.
Do NOT expose chain-of-thought.
If data is insufficient, explicitly say so.

USER:
""" + userPrompt;
    }
}