package sonnh.dev.assetsmanagement.statement;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinancialStatement {

    private final BigDecimal freeCashFlow;   // FCF hiện tại
    private final BigDecimal growthRate;   // tăng trưởng kỳ vọng
    private final BigDecimal eps;

    public FinancialStatement(BigDecimal freeCashFlow, BigDecimal growthRate, BigDecimal eps) {
        this.freeCashFlow = freeCashFlow;
        this.growthRate = growthRate;
        this.eps = eps;
    }

    public FinancialStatement(BigDecimal freeCashFlow, BigDecimal growthRate) {
        this.freeCashFlow = freeCashFlow;
        this.growthRate = growthRate;
        this.eps = BigDecimal.ZERO;
    }
}

