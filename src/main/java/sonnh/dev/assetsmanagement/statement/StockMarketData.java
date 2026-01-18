package sonnh.dev.assetsmanagement.statement;

import java.math.BigDecimal;

public class StockMarketData {

    private final BigDecimal currentPrice;
    private final BigDecimal beta;
    private final BigDecimal sectorPE;

    public StockMarketData(BigDecimal currentPrice,
                           BigDecimal beta,
                           BigDecimal sectorPE) {
        this.currentPrice = currentPrice;
        this.beta = beta;
        this.sectorPE = sectorPE;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public BigDecimal getBeta() {
        return beta;
    }

    public BigDecimal getSectorPE() {
        return sectorPE;
    }
}
