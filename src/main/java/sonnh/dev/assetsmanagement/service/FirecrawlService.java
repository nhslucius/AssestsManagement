package sonnh.dev.assetsmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.request.FirecrawlScrapeRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FirecrawlService {

    @Value("${firecrawl.api.key}")
    private String apiKey;

    private static final String SCRAPE_ENDPOINT = "https://api.firecrawl.dev/scrape";

    public String scrapeUrl(String targetUrl) throws IOException, ParseException {

        FirecrawlScrapeRequest body = new FirecrawlScrapeRequest();
        body.setUrl(targetUrl);
        body.setFormats(Arrays.asList("markdown", "html"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(body);

        HttpPost post = new HttpPost(SCRAPE_ENDPOINT);
        post.setHeader("Authorization", "Bearer " + apiKey);
        post.setHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(jsonBody, StandardCharsets.UTF_8));

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {

            int status = response.getCode();
            if (status >= 200 && status < 300) {
                return EntityUtils.toString(response.getEntity());
            } else {
                String msg = EntityUtils.toString(response.getEntity());
                throw new IOException("Firecrawl request failed: " + status + " " + msg);
            }
        }
    }
}

