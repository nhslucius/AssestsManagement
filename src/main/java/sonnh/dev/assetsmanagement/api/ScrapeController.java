package sonnh.dev.assetsmanagement.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sonnh.dev.assetsmanagement.dto.StockSnapshot;
import sonnh.dev.assetsmanagement.response.ApiResponse;
import sonnh.dev.assetsmanagement.service.FireAntScrapeService;
import sonnh.dev.assetsmanagement.utils.FireAntJsoupParser;

@RestController
@RequestMapping("/api/scrape")
public class ScrapeController {

    private final FireAntScrapeService service;

    public ScrapeController(FireAntScrapeService service) {
        this.service = service;
    }

    @GetMapping("/fireant")
    public ApiResponse<StockSnapshot> scrapeFireAnt(@RequestParam String symbol) {

        String url = "https://fireant.vn/dashboard/content/symbols/" + symbol;
        String html = service.loadRenderedHtml(url);
        Document doc = Jsoup.parse(html);

        FireAntJsoupParser parser = new FireAntJsoupParser();
        StockSnapshot result = parser.parse(html);

        return ApiResponse.success(result);
    }
}
