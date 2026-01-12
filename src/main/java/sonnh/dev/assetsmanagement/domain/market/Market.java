package sonnh.dev.assetsmanagement.domain.market;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Market {
    private String state;
    private BigDecimal inflationRate;
    private BigDecimal volatility;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(BigDecimal inflationRate) {
        this.inflationRate = inflationRate;
    }

    public BigDecimal getVolatility() {
        return volatility;
    }

    public void setVolatility(BigDecimal volatility) {
        this.volatility = volatility;
    }
}
