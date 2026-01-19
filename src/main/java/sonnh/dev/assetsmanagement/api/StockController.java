package sonnh.dev.assetsmanagement.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sonnh.dev.assetsmanagement.dto.StockOverviewDto;
import sonnh.dev.assetsmanagement.dto.StockSnapshotDto;
import sonnh.dev.assetsmanagement.service.StockCrawlerService;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockCrawlerService stockCrawlerService;

    @GetMapping("/{code}")
    public StockOverviewDto overview(@PathVariable String code) {
        return stockCrawlerService.crawlOverview(code);
    }
}