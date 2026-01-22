package sonnh.dev.assetsmanagement.gemini;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeminiModelFallback {

    public List<GeminiModel> getFallbackChain() {
        return List.of(
                GeminiModel.GEMINI_3_FLASH,
                GeminiModel.GEMINI_2_5_FLASH,
                GeminiModel.GEMINI_2_5_FLASH_LITE,
                GeminiModel.GEMMA_3_27B,
                GeminiModel.GEMMA_3_12B,
                GeminiModel.GEMMA_3_4B
        );
    }
}

