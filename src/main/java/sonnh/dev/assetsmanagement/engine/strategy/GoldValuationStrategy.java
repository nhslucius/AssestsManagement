package sonnh.dev.assetsmanagement.engine.strategy;

import org.springframework.stereotype.Component;
import sonnh.dev.assetsmanagement.engine.context.AssetContext;

import java.math.BigDecimal;

@Component("GOLD_SIMPLE")
public class GoldValuationStrategy implements ValuationStrategy {
    public BigDecimal calculate(AssetContext ctx) {
        BigDecimal base = ctx.getAsset().getBaseValue();
        BigDecimal marketFactor = ctx.getMarket().getInflationRate();
        return base.multiply(marketFactor);
    }
}
