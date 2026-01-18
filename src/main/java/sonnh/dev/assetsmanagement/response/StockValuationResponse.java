package sonnh.dev.assetsmanagement.response;

import lombok.Data;
import sonnh.dev.assetsmanagement.constant.ValuationSignal;
import sonnh.dev.assetsmanagement.result.StockValuationResult;

import java.math.BigDecimal;

@Data
public class StockValuationResponse {

    public String ticker;
    public BigDecimal intrinsicValue;
    public BigDecimal marketPrice;
    public ValuationSignal signal;
    public String explanation;

    public static StockValuationResponse toResponse(String ticker,
                                              StockValuationResult result) {

        StockValuationResponse response = new StockValuationResponse();
        response.ticker = ticker;
        response.intrinsicValue = result.getIntrinsicValue();
        response.marketPrice = result.getMarketPrice();
        response.signal = result.getSignal();
        response.explanation = result.getExplanation();
        return response;
    }

}

