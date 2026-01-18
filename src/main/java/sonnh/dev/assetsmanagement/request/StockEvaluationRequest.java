package sonnh.dev.assetsmanagement.request;

import lombok.Data;
import sonnh.dev.assetsmanagement.constant.MarketRegime;

import java.math.BigDecimal;

@Data
public class StockEvaluationRequest {

    private String ticker; // HPG

    // LAYER 1 – THESIS
    private double thesisScore; // 0.0 – 1.0

    // LAYER 2 – VALUATION
    private ValuationInput valuation;

    // LAYER 3 – TECHNICAL + FLOW
    private TechnicalInput technical;

    // LAYER 4 – RISK / REWARD
    private RiskRewardInput riskReward;

    @Data
    public static class ValuationInput {

        // Relative
        private double peForward;
        private double peHistoricalAvg;
        private double peSectorAvg;

        // Earnings-based
        private double epsMidCycle;
        private double targetPe;

        // Consensus
        private double consensusTargetPrice;
        private double currentPrice;
    }

    @Data
    public static class TechnicalInput {

        private double rsi;          // 0–100
        private boolean macdPositive;
        private boolean obvUptrend;
        private boolean accumulationPhase;
    }


    @Data
    public static class RiskRewardInput {

        private double entryPrice;
        private double stopLoss;
        private double targetPrice;
    }

}


