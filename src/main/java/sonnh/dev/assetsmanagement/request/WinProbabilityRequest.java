package sonnh.dev.assetsmanagement.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WinProbabilityRequest {

    private String stockCode;
    private BigDecimal holdingPrice;
}

