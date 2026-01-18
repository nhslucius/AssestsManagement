package sonnh.dev.assetsmanagement.service;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;

@Service
public class FireAntScrapeService {

    public String loadRenderedHtml(String url) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions()
                            .setHeadless(true));

            Page page = browser.newPage();

            page.navigate(url);
            page.waitForLoadState(LoadState.NETWORKIDLE);

            return page.content();
        }
    }

//    public StockData parse(String html) {
//
//        Document doc = Jsoup.parse(html);
//
//        String price = doc.select("div.price").text();
//        String pe = doc.select("span.pe").text();
//
//        return new StockData(price, pe);
//    }

}

