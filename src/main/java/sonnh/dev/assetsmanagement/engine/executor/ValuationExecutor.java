package sonnh.dev.assetsmanagement.engine.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sonnh.dev.assetsmanagement.engine.context.AssetContext;
import sonnh.dev.assetsmanagement.engine.definition.ValuationRuleDefinition;
import sonnh.dev.assetsmanagement.engine.provider.RuleProvider;
import sonnh.dev.assetsmanagement.engine.resolver.ValuationStrategyResolver;
import sonnh.dev.assetsmanagement.engine.strategy.ValuationStrategy;

import java.math.BigDecimal;

@Component
public class ValuationExecutor {

    @Autowired
    RuleProvider ruleProvider;

    @Autowired
    ValuationStrategyResolver resolver;

    public BigDecimal valuate(AssetContext ctx) {
        ValuationRuleDefinition rule =
                ruleProvider.getRule(ctx.getAsset().getType(), ctx.getMarket().getState());

        ValuationStrategy strategy = resolver.resolve(rule);

        BigDecimal result = strategy.calculate(ctx);
        ctx.setResult(result);
        return result;
    }
}
