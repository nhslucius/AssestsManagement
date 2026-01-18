package sonnh.dev.assetsmanagement.result;

import lombok.Data;
import sonnh.dev.assetsmanagement.constant.ValuationSignal;

import java.math.BigDecimal;
@Data
public class StockValuationResult implements EngineResult {

    private final BigDecimal intrinsicValue;
    private final BigDecimal marketPrice;
    private final ValuationSignal signal;
    private final String explanation;

    public StockValuationResult(BigDecimal intrinsicValue, BigDecimal marketPrice, ValuationSignal signal, String explanation) {
        this.intrinsicValue = intrinsicValue;
        this.marketPrice = marketPrice;
        this.signal = signal;
        this.explanation = explanation;
    }
}
