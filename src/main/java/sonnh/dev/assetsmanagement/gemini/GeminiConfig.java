package sonnh.dev.assetsmanagement.gemini;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GeminiConfig {

    private final GeminiProperties properties;

    @Bean
    public com.google.genai.Client geminiClient() {
        return com.google.genai.Client.builder()
                .apiKey(properties.getApiKey())
                .build();
    }
}
