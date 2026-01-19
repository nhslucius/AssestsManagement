package sonnh.dev.assetsmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinancialStrengthDto {

    private BigDecimal epsStrength;      // %
    private BigDecimal roeStrength;      // %
    private BigDecimal efficiencyScore; // 3.3 / 5
    private BigDecimal pbStrength;       // %
    private BigDecimal priceGrowth;      // %
}

