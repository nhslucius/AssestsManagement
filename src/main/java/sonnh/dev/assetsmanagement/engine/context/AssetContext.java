package sonnh.dev.assetsmanagement.engine.context;

import lombok.Data;
import sonnh.dev.assetsmanagement.domain.asset.Asset;
import sonnh.dev.assetsmanagement.domain.market.Market;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

//Trạng thái tính toán

public class AssetContext {
    private Asset asset;
    private Market market;
    private BigDecimal result;

    private Map<String, Object> attributes = new HashMap<>();

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}

