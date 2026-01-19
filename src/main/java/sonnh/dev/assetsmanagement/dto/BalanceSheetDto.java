package sonnh.dev.assetsmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceSheetDto {
    private String debt;
    private String equity;
    private BigDecimal debtToEquity;
    private BigDecimal equityToAsset;
    private String cash;
}

