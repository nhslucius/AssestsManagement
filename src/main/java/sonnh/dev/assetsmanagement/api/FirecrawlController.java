package sonnh.dev.assetsmanagement.api;

import org.apache.hc.core5.http.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sonnh.dev.assetsmanagement.service.FirecrawlService;

import java.io.IOException;

@RestController
@RequestMapping("/api/firecrawl")
public class FirecrawlController {

    private final FirecrawlService firecrawlService;

    public FirecrawlController(FirecrawlService firecrawlService) {
        this.firecrawlService = firecrawlService;
    }

    @GetMapping("/scrape")
    public ResponseEntity<String> scrape(@RequestParam String url) {
        try {
            String result = firecrawlService.scrapeUrl(url);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error scraping URL: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

