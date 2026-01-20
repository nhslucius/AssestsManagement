package sonnh.dev.assetsmanagement.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sonnh.dev.assetsmanagement.request.PromptRequest;
import sonnh.dev.assetsmanagement.request.WinProbabilityRequest;
import sonnh.dev.assetsmanagement.service.LmStudioService;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class LmStudioController {

    private final LmStudioService lmStudioService;

    @PostMapping("/prompt")
    public String sendPrompt(@RequestBody WinProbabilityRequest request) {
        return lmStudioService.sendPrompt(request);
    }
}
