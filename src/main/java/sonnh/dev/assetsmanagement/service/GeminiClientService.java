package sonnh.dev.assetsmanagement.service;

import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.gemini.GeminiProperties;

@Service
@RequiredArgsConstructor
public class GeminiClientService {

    private final com.google.genai.Client client;
    private final GeminiProperties properties;

    public String generateText(String prompt) {

        GenerateContentResponse response =
                client.models.generateContent(
                        properties.getModel(),
                        prompt,
                        null
                );

        System.out.println(response.text());
        return response.text();
    }
}

