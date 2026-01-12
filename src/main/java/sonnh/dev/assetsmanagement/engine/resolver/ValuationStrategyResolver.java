package sonnh.dev.assetsmanagement.engine.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sonnh.dev.assetsmanagement.engine.definition.ValuationRuleDefinition;
import sonnh.dev.assetsmanagement.engine.strategy.ValuationStrategy;

import java.util.Map;

@Component
public class ValuationStrategyResolver {

    @Autowired
    private Map<String, ValuationStrategy> strategies;

    public ValuationStrategy resolve(ValuationRuleDefinition def) {
        return strategies.get(def.getAssetType() + "_" + def.getStrategy());
    }
}

