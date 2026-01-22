package sonnh.dev.assetsmanagement.service;

import com.google.genai.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.gemini.GeminiModelFallback;
import sonnh.dev.assetsmanagement.gemini.GeminiModel;

@Service
@RequiredArgsConstructor
public class GeminiFallbackService {

    private final Client geminiClient;
    private final GeminiModelFallback modelFallback;

    public String generateWithFallback(String prompt) {

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
                    // log & thử model tiếp theo
                    continue;
                }

                // lỗi khác → fail luôn
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
