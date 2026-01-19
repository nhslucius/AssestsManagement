package sonnh.dev.assetsmanagement.dto;

import lombok.Data;

@Data
public class StockOverviewDto {

    private PriceSummaryDto priceSummary;
    private ValuationDto valuation;
    private MarketMetricDto marketMetric;
    private BalanceSheetDto balanceSheet;
    private FinancialStrengthDto financialStrength;
}

