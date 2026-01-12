package sonnh.dev.assetsmanagement.engine.strategy;

import sonnh.dev.assetsmanagement.engine.context.AssetContext;

import java.math.BigDecimal;

public interface ValuationStrategy {
    BigDecimal calculate(AssetContext ctx);
}
