package sonnh.dev.assetsmanagement.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.dto.*;
import sonnh.dev.assetsmanagement.exeption.CrawlException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class StockCrawlerService {

    private final String defaultUserAgent;

    private final StockCacheService cacheService;

    public StockOverviewDto crawlOverview(String stockCode) {

        // 1. Check cache
        StockOverviewDto cached = cacheService.get(stockCode);
        if (cached != null) {
            return cached;
        }

        // 2. Crawl web
        StockOverviewDto fresh = crawlFromWeb(stockCode);

        // 3. Save cache (TTL = 16h)
        cacheService.put(stockCode, fresh);

        return fresh;
    }

    private StockOverviewDto crawlFromWeb(String stockCode) {

        String url = "https://www.cophieu68.vn/quote/summary.php?id=" + stockCode;
        System.out.println("crawlFromWeb " + url);
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent(defaultUserAgent)
                    .timeout(10_000)
                    .get();

            return parseOverview(doc);

        } catch (IOException e) {
            throw new CrawlException(
                    "Failed to crawl stock " + stockCode, e
            );
        }
    }

    /**
     * Parse block: <div class="flex_row overflow">
     */
    private StockOverviewDto parseOverview(Document doc) {
        Element overview = doc.selectFirst("div.flex_row.overflow");
        if (overview == null) {
            throw new CrawlException("Overview block not found", null);
        }

        Elements sections = overview.select("> div.flex_row");

        StockOverviewDto dto = new StockOverviewDto();
        dto.setCompanyName(parseCompanyName(doc));
        dto.setCurrentPrice(parseCurrentPrice(doc));
        dto.setPriceSummary(parsePriceSummary(sections.get(0)));
        dto.setValuation(parseValuation(sections.get(1)));
        dto.setMarketMetric(parseMarketMetric(sections.get(2)));
        dto.setBalanceSheet(parseBalanceSheet(sections.get(3)));
        dto.setFinancialStrength(parseFinancialStrength(sections.get(4)));

        return dto;
    }

    private PriceSummaryDto parsePriceSummary(Element section) {

        Elements values = section
                .select(".flex_detail.bold > div");

        if (values.size() < 5) {
            throw new CrawlException("Invalid price summary block", null);
        }

        PriceSummaryDto dto = new PriceSummaryDto();
        dto.setReferencePrice(parseDecimal(values.get(0).text()));
        dto.setOpenPrice(parseDecimal(values.get(1).text()));
        dto.setHighPrice(parseDecimal(values.get(2).text()));
        dto.setLowPrice(parseDecimal(values.get(3).text()));
        dto.setVolume(
                Long.parseLong(values.get(4).attr("data-value"))
        );

        return dto;
    }


    private ValuationDto parseValuation(Element section) {
        Elements values = section
                .select(".flex_detail.bold div");

        ValuationDto dto = new ValuationDto();
        dto.setBookValue(parseDecimal(values.get(0).text()));
        dto.setEps(parseDecimal(values.get(1).text()));
        dto.setPe(parseDecimal(values.get(2).text()));
        dto.setPb(parseDecimal(values.get(3).text()));

        String[] roaRoe = values.get(4).text().split("#");
        dto.setRoa(parsePercent(roaRoe[0]));
        dto.setRoe(parsePercent(roaRoe[1]));

        return dto;
    }

    private MarketMetricDto parseMarketMetric(Element section) {
        Elements values = section
                .select(".flex_detail.bold div");

        MarketMetricDto dto = new MarketMetricDto();
        dto.setBeta(parseDecimal(values.get(0).text()));
        dto.setMarketCap(values.get(1).text().replace("Bi", "Tỷ").replace("Mi", "triệu"));
        dto.setListedVolume(values.get(2).text().replace("Bi", "Tỷ").replace("Mi", "triệu"));
        dto.setAvgVolume52w(parseLong(values.get(3).text()));

        String[] highLow = values.get(4).text().split("-");
        dto.setHigh52w(parseDecimal(highLow[0]));
        dto.setLow52w(parseDecimal(highLow[1]));

        return dto;
    }

    private BalanceSheetDto parseBalanceSheet(Element section) {
        Elements values = section
                .select(".flex_detail.bold div");

        BalanceSheetDto dto = new BalanceSheetDto();
        dto.setDebt(values.get(0).text().replace("Bi", "Tỷ"));
        dto.setEquity(values.get(1).text().replace("Bi", "Tỷ"));
        dto.setDebtToEquity(parsePercent(values.get(2).text()));
        dto.setEquityToAsset(parsePercent(values.get(3).text()));
        dto.setCash(values.get(4).text().replace("Bi", "Tỷ"));

        return dto;
    }

    private FinancialStrengthDto parseFinancialStrength(Element section) {
        Elements blocks = section
                .select(".flex_detail.bold > div");

        FinancialStrengthDto dto = new FinancialStrengthDto();

        dto.setEpsStrength(parsePercent(blocks.get(0).text()));
        dto.setRoeStrength(parsePercent(blocks.get(1).text()));

        String efficiency = blocks.get(2).text().split("/")[0];
        dto.setEfficiencyScore(parseDecimal(efficiency));

        dto.setPbStrength(parsePercent(blocks.get(3).text()));
        dto.setPriceGrowth(parsePercent(blocks.get(4).text()));

        return dto;
    }

    private BigDecimal parseDecimal(String raw) {

        if (raw == null || raw.isBlank()) {
            return null;
        }

        // lấy số đầu tiên xuất hiện
        Matcher m = Pattern
                .compile("[-+]?[0-9]*\\.?[0-9]+")
                .matcher(raw.replace(",", ""));

        if (!m.find()) {
            throw new CrawlException("Cannot parse decimal from: " + raw, null);
        }

        return new BigDecimal(m.group());
    }


    private BigDecimal parsePercent(String raw) {
        BigDecimal value = parseDecimal(raw);
        return value;
    }


    private Long parseLong(String raw) {
        Matcher m = Pattern
                .compile("[0-9]+")
                .matcher(raw.replace(",", ""));

        if (!m.find()) {
            throw new CrawlException("Cannot parse long from: " + raw, null);
        }
        return Long.parseLong(m.group());
    }

    private BigDecimal parseCurrentPrice(Document doc) {

        Element el = doc.selectFirst("#stockname_close");

        if (el == null) {
            throw new CrawlException("Current price element not found", null);
        }

        return parseDecimal(el.text());
    }

    private String parseCompanyName(Document doc) {

        Element h1 = doc.selectFirst("div.margin_wrap h1");

        if (h1 == null) {
            throw new CrawlException("Company name not found", null);
        }

        // Clone để không ảnh hưởng DOM gốc
        Element cloned = h1.clone();

        // Remove span chứa mã cổ phiếu
//        cloned.select("span").remove();

        return cloned.text().trim();
    }

}
