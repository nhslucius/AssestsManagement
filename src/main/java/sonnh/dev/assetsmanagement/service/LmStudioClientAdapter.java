package sonnh.dev.assetsmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sonnh.dev.assetsmanagement.client.AiClient;
import sonnh.dev.assetsmanagement.request.WinProbabilityRequest;

@Component
@RequiredArgsConstructor
public class LmStudioClientAdapter implements AiClient {

    private final LmStudioService lmStudioService;

    @Override
    public String generate(String prompt) {
        WinProbabilityRequest request = new WinProbabilityRequest();
        return lmStudioService.calculateByLmStudio(request);
    }
}

