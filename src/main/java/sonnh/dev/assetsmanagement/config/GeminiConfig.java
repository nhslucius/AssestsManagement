package sonnh.dev.assetsmanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sonnh.dev.assetsmanagement.gemini.GeminiProperties;

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
