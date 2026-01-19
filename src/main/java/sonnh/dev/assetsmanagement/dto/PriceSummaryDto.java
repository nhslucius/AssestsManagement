package sonnh.dev.assetsmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceSummaryDto {
    private BigDecimal referencePrice;
    private BigDecimal openPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private Long volume;
}

