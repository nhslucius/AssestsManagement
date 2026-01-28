package sonnh.dev.assetsmanagement.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sonnh.dev.assetsmanagement.client.AiClient;
import sonnh.dev.assetsmanagement.client.OllamaClientAdapter;
import sonnh.dev.assetsmanagement.constant.AiProvider;
import sonnh.dev.assetsmanagement.deepseek.DeepSeekClientAdapter;
import sonnh.dev.assetsmanagement.gemini.GeminiClientAdapter;
import sonnh.dev.assetsmanagement.service.LmStudioClientAdapter;

@RequiredArgsConstructor
@Component
public class AiClientFactory {

    private final GeminiClientAdapter geminiClient;
    private final LmStudioClientAdapter lmStudioClient;
    private final OllamaClientAdapter ollamaClient;
    private final DeepSeekClientAdapter deepSeekClient;

    public AiClient getClient(AiProvider provider) {
        return switch (provider) {
            case GEMINI -> geminiClient;
            case LM_STUDIO -> lmStudioClient;
            case OLLAMA -> ollamaClient;
            case DEEPSEEK -> deepSeekClient;
        };
    }
}
