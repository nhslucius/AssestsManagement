package sonnh.dev.assetsmanagement.engine.provider;

import org.springframework.stereotype.Component;
import sonnh.dev.assetsmanagement.engine.definition.ValuationRuleDefinition;

import java.util.Map;

@Component
public class InMemoryRuleProvider implements RuleProvider {

    private Map<String, ValuationRuleDefinition> rules = Map.of(
            "GOLD_BULL", new ValuationRuleDefinition("GOLD", "BULL", "SIMPLE"),
            "STOCK_BEAR", new ValuationRuleDefinition("STOCK", "BEAR", "VOLATILITY")
    );

    public ValuationRuleDefinition getRule(String asset, String market) {
        return rules.get(asset + "_" + market);
    }
}

