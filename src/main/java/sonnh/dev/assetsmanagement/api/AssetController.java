package sonnh.dev.assetsmanagement.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sonnh.dev.assetsmanagement.engine.context.AssetContext;
import sonnh.dev.assetsmanagement.engine.executor.ValuationExecutor;

import java.math.BigDecimal;

@RestController
@RequestMapping("/assets")
public class AssetController {

    @Autowired
    ValuationExecutor executor;

    @PostMapping("/valuate")
    public BigDecimal valuate(@RequestBody AssetContext ctx) {
        return executor.valuate(ctx);
    }
}
