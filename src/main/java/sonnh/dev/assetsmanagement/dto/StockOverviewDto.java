package sonnh.dev.assetsmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockOverviewDto {
    private String companyName;
    private BigDecimal currentPrice;
    private PriceSummaryDto priceSummary;
    private ValuationDto valuation;
    private MarketMetricDto marketMetric;
    private BalanceSheetDto balanceSheet;
    private FinancialStrengthDto financialStrength;
}

