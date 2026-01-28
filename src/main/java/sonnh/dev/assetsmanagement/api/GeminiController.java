package sonnh.dev.assetsmanagement.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sonnh.dev.assetsmanagement.request.WinProbabilityRequest;
import sonnh.dev.assetsmanagement.service.AlphaWinProbabilityService;
import sonnh.dev.assetsmanagement.service.LmStudioService;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class GeminiController {

    private final AlphaWinProbabilityService service;
    private final LmStudioService lmStudioService;

    @PostMapping("/win-probability/gemini")
    public String calculateByGemini(@RequestBody WinProbabilityRequest request) {
        return service.calculateByGemini(request);
    }

    @PostMapping("/win-probability/gemini/v2")
    public String calculateByGemini2(@RequestBody WinProbabilityRequest request) {
        return service.calculateByGemini2(request);
    }

    @PostMapping("/gen-prompt")
    public String genPrompt(@RequestBody WinProbabilityRequest request) {
        return service.genPrompt(request);
    }

    @PostMapping("/gen-prompt/v2")
    public String genPromptVer2(@RequestBody WinProbabilityRequest request) {
        return service.genPrompt2(request);
    }

    @PostMapping("/win-probability/ollama")
    public String calculateByOllama(@RequestBody WinProbabilityRequest request) {
        return service.calculateByOllama(request);
    }

    @PostMapping("/win-probability/deepseek")
    public String calculateByDeepSeek(@RequestBody WinProbabilityRequest request) {
        return service.calculateByDeepSeek(request);
    }

    @PostMapping("/win-probability/lm-studio")
    public String calculateByLmStudio(@RequestBody WinProbabilityRequest request) {
        return lmStudioService.calculateByLmStudio(request);
    }
}

