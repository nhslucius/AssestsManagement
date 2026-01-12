package sonnh.dev.assetsmanagement.engine.definition;

public class ValuationRuleDefinition {
    private String assetType;     // GOLD, STOCK, REAL_ESTATE
    private String marketState;   // BULL, BEAR, CRISIS
    private String strategy;      // SIMPLE, VOLATILITY, REAL_ESTATE

    public ValuationRuleDefinition(String gold, String bull, String simple) {
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getMarketState() {
        return marketState;
    }

    public void setMarketState(String marketState) {
        this.marketState = marketState;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}

