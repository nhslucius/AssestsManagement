package sonnh.dev.assetsmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MarketMetricDto {
    private BigDecimal beta;
    private String marketCap;      // để String vì có "Bi"
    private String listedVolume;   // "Mi"
    private Long avgVolume52w;
    private BigDecimal high52w;
    private BigDecimal low52w;
}
