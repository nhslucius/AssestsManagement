package sonnh.dev.assetsmanagement.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sonnh.dev.assetsmanagement.dto.StockSnapshot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;

public class FireAntJsoupParser {

    public StockSnapshot parse(String html) {

        Document doc = Jsoup.parse(html);
        StockSnapshot s = new StockSnapshot();

        s.setSymbol(parseSymbol(doc));

        s.setReferencePrice(parseDoubleByLabel(doc, "Tham chiếu"));
        s.setOpenPrice(parseDoubleByLabel(doc, "Mở cửa"));

        parseLowHigh(doc, s);

        s.setVolume(parseLongByLabel(doc, "Khối lượng"));
        s.setTradedValue(parseBilionByLabel(doc, "Giá trị"));
        s.setAvgVolume10d(parseLongByLabel(doc, "KLTB 10 ngày"));

        s.setBeta(parseDoubleByLabel(doc, "Beta"));
        s.setMarketCap(parseBilionByLabel(doc, "Thị giá vốn"));
        s.setSharesOutstanding(parseLongByLabel(doc, "Số lượng CPLH"));

        s.setPe(parseDoubleByLabel(doc, "P/E"));
        s.setEps(parseDoubleByLabel(doc, "EPS"));

        // Close price = tham chiếu nếu thị trường đóng cửa
        s.setClosePrice(s.getReferencePrice());

        return s;
    }

    private String parseSymbol(Document doc) {
        Element h4 = doc.selectFirst("h4.bp5-heading");
        return h4 != null ? h4.text().trim() : null;
    }


    private double parseCurrentPrice(Document doc) {
        Element priceEl = doc.selectFirst("div.sc-dxlmjS span");
        return priceEl != null ? parseDouble(priceEl.text()) : 0.0;
    }

//    private void parseChange(Document doc, StockSnapshot s) {
//        Element el = doc.selectFirst("div.sc-fedTIj span");
//        if (el == null) return;
//
//        String[] parts = el.text().replace("%", "").split("/");
//        if (parts.length == 2) {
//            s.setChange(parseDouble(parts[0]));
//            s.setChangePercent(parseDouble(parts[1]));
//        }
//    }

//    private void parseOHLC(Document doc, StockSnapshot s) {
//
//        Element ohlcEl = doc.selectFirst("div:contains(O) strong");
//        if (ohlcEl == null) return;
//
//        String text = ohlcEl.parent().text();
//
//        s.setOpen(extractValue(text, "O"));
//        s.setHigh(extractValue(text, "H"));
//        s.setLow(extractValue(text, "L"));
//        s.setClose(extractValue(text, "C"));
//    }

    private Long parseVolume(Document doc) {
        Element el = doc.selectFirst("div:contains(Vol)");
        if (el == null) return null;

        return parseVolumeValue(el.text());
    }

    private Double parseTableValue(Document doc, String label) {

        Element row = doc.selectFirst("tr:has(td:contains(" + label + "))");
        if (row == null) return null;

        Element valueTd = row.select("td").get(1);
        return parseDouble(valueTd.text());
    }

    private Double parseMarketCap(Document doc) {

        Element row = doc.selectFirst("tr:has(td:contains(Thị giá vốn))");
        if (row == null) return null;

        String text = row.select("td").get(1).text();
        return parseDouble(text.replace("tỷ", ""));
    }

    private double parseDouble(String s) {
        return Double.parseDouble(
                s.replace(",", "")
                        .replace("+", "")
                        .trim()
        );
    }


    private Double extractValue(String text, String key) {
        Pattern p = Pattern.compile(key + "\\s*(\\d+\\.\\d+)");
        Matcher m = p.matcher(text);
        return m.find() ? Double.parseDouble(m.group(1)) : null;
    }

    private Long parseVolumeValue(String text) {
        Pattern p = Pattern.compile("Vol\\s*(\\d+\\.\\d+)M");
        Matcher m = p.matcher(text);
        if (!m.find()) return null;
        return (long) (Double.parseDouble(m.group(1)) * 1_000_000);
    }

    private String getValue(Document doc, String label) {

        Element row = doc.selectFirst(
                "table.table-data tr:has(td:matchesOwn(^" + label + "$))"
        );

        if (row == null) return null;

        return row.select("td").get(1).text().trim();
    }

    private void parseLowHigh(Document doc, StockSnapshot s) {

        Element row = findRow(doc, "Thấp - Cao");
        if (row == null) return;

        Elements spans = row.select("td").get(1).select("span");
        if (spans.size() >= 2) {
            s.setLowPrice(parseDouble(spans.get(0).text()));
            s.setHighPrice(parseDouble(spans.get(1).text()));
        }
    }

    private Element findRow(Document doc, String label) {
        return doc.selectFirst("tr:has(td:matchesOwn(^" + label + "$))");
    }

    private Double parseDoubleByLabel(Document doc, String label) {

        Element row = findRow(doc, label);
        if (row == null) return null;

        String text = row.select("td").get(1).text();
        return parseDouble(text);
    }

    private Long parseLongByLabel(Document doc, String label) {

        Element row = findRow(doc, label);
        if (row == null) return null;

        String text = row.select("td").get(1).text();
        return parseLong(text);
    }

    private Double parseBilionByLabel(Document doc, String label) {

        Element row = findRow(doc, label);
        if (row == null) return null;

        String text = row.select("td").get(1).text();
        return parseDouble(text.replace("tỷ", ""));
    }

    private long parseLong(String s) {
        return Long.parseLong(
                s.replace(",", "")
                        .trim()
        );
    }


}

