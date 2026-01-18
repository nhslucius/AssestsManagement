package sonnh.dev.assetsmanagement.request;

import lombok.Data;

import java.util.List;

@Data
public class FirecrawlScrapeRequest {
    private String url;
    private List<String> formats;

    // constructors, getters, setters
}

