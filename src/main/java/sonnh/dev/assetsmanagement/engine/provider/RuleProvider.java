package sonnh.dev.assetsmanagement.engine.provider;

import sonnh.dev.assetsmanagement.engine.definition.ValuationRuleDefinition;

public interface RuleProvider {
    ValuationRuleDefinition getRule(String assetType, String marketState);
}
