package sonnh.dev.assetsmanagement.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sonnh.dev.assetsmanagement.request.StockEvaluationRequest;
import sonnh.dev.assetsmanagement.service.*;

import java.util.Map;

@RestController
@RequestMapping("/api/valuation")
@RequiredArgsConstructor
public class ValuationController {

    private final ThesisService thesisService;
    private final ValuationService valuationService;
    private final TechnicalService technicalService;
    private final RiskRewardService riskRewardService;
    private final WinProbabilityService winService;

    @PostMapping("/evaluate")
    public Map<String, Object> evaluate(
            @RequestBody StockEvaluationRequest request) {

        double thesis = thesisService.score(request.getThesisScore());
        double valuation = valuationService.calculateValuationEdge(request.getValuation());
        double technical = technicalService.score(request.getTechnical());
        double rr = riskRewardService.score(request.getRiskReward());

        double winProb = winService.calculate(thesis, valuation, technical, rr);

        return Map.of(
                "ticker", request.getTicker(),
                "winProbability", Math.round(winProb * 100) + "%",
                "breakdown", Map.of(
                        "thesis", thesis,
                        "valuation", valuation,
                        "technical", technical,
                        "riskReward", rr
                )
        );
    }
}

