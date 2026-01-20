package sonnh.dev.assetsmanagement.api;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sonnh.dev.assetsmanagement.request.WinProbabilityRequest;
import sonnh.dev.assetsmanagement.service.AlphaWinProbabilityService;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class GeminiController {

    private final AlphaWinProbabilityService service;

    @PostMapping("/win-probability")
    public String calculate(@RequestBody WinProbabilityRequest request) {
        return service.calculate(request);
    }

    @PostMapping("/win-probability/v2")
    public String calculate2(@RequestBody WinProbabilityRequest request) {
        return service.calculate2(request);
    }

    @PostMapping("/gen-prompt")
    public String genPrompt(@RequestBody WinProbabilityRequest request) {
        return service.genPrompt(request);
    }

    @PostMapping("/gen-prompt/v2")
    public String genPromptVer2(@RequestBody WinProbabilityRequest request) {
        return service.genPrompt2(request);
    }
}

