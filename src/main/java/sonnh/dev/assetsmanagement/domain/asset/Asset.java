package sonnh.dev.assetsmanagement.domain.asset;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Asset {
    private String type;
    private BigDecimal baseValue;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(BigDecimal baseValue) {
        this.baseValue = baseValue;
    }
}
