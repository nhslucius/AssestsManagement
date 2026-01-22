package sonnh.dev.assetsmanagement.gemini;

import com.google.genai.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sonnh.dev.assetsmanagement.client.AiClient;

@Component
@RequiredArgsConstructor
public class GeminiClientAdapter implements AiClient {

    private final Client geminiClient;
    private final GeminiModelFallback modelFallback;

    @Override
    public String generate(String prompt) {

        for (GeminiModel model : modelFallback.getFallbackChain()) {
            try {

                return geminiClient
                        .models
                        .generateContent(
                                model.getModelName(),
                                prompt,
                                null
                        )
                        .text();

            } catch (Exception ex) {

                if (isRateLimit(ex)) {
                    continue; // thử model tiếp theo
                }

                throw new RuntimeException(
                        "Gemini failed on model " + model.getModelName(), ex
                );
            }
        }

        throw new RuntimeException("All Gemini models are rate-limited");
    }

    private boolean isRateLimit(Exception ex) {
        String msg = ex.getMessage().toLowerCase();
        return msg.contains("rate")
                || msg.contains("quota")
                || msg.contains("limit")
                || msg.contains("429");
    }
}
