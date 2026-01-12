package sonnh.dev.assetsmanagement.engine.strategy;

import org.springframework.stereotype.Component;
import sonnh.dev.assetsmanagement.engine.context.AssetContext;

import java.math.BigDecimal;

@Component("STOCK_VOLATILITY")
public class StockValuationStrategy implements ValuationStrategy {
    public BigDecimal calculate(AssetContext ctx) {
        BigDecimal base = ctx.getAsset().getBaseValue();
        BigDecimal vol = ctx.getMarket().getVolatility();
        return base.multiply(vol);
    }
}
