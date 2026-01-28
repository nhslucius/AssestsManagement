package sonnh.dev.assetsmanagement.deepseek;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sonnh.dev.assetsmanagement.client.AiClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeepSeekClientAdapter implements AiClient {

    private final RestTemplate restTemplate;

    @Value("${deepseek.base-url}")
    private String baseUrl;

    @Value("${deepseek.api-key}")
    private String apiKey;

    @Value("${deepseek.model}")
    private String model;

    @Override
    public String generate(String prompt) {

        String url = baseUrl + "/chat/completions";

        DeepSeekChatRequest request = new DeepSeekChatRequest();
        request.setModel(model);
        request.setStream(false);
        request.setMessages(List.of(
                new DeepSeekChatRequest.Message(
                        "system",
                        "You are a helpful assistant."
                ),
                new DeepSeekChatRequest.Message(
                        "user",
                        sanitizePrompt(prompt)
                )
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<DeepSeekChatRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<DeepSeekChatResponse> response =
                restTemplate.postForEntity(
                        url,
                        entity,
                        DeepSeekChatResponse.class
                );

        if (response.getBody() == null
                || response.getBody().getChoices() == null
                || response.getBody().getChoices().isEmpty()) {
            throw new RuntimeException("Empty response from DeepSeek");
        }

        return response.getBody()
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }

    private String sanitizePrompt(String prompt) {
        if (prompt == null) return null;
        return prompt.replace("\r\n", "\n").replace("\r", "\n");
    }
}

