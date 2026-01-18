package sonnh.dev.assetsmanagement.service;

import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.request.StockEvaluationRequest;

@Service
public class RiskRewardService {

    public double score(StockEvaluationRequest.RiskRewardInput rr) {

        double risk = rr.getEntryPrice() - rr.getStopLoss();
        double reward = rr.getTargetPrice() - rr.getEntryPrice();

        double rrRatio = reward / risk;

        if (rrRatio >= 3) return 0.8;
        if (rrRatio >= 2) return 0.6;
        if (rrRatio >= 1.5) return 0.4;

        return 0.2;
    }
}

