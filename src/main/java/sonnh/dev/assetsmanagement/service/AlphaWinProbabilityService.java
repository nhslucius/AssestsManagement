package sonnh.dev.assetsmanagement.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.dto.*;
import sonnh.dev.assetsmanagement.request.WinProbabilityRequest;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AlphaWinProbabilityService {

    private final StockCrawlerService stockCrawlerService;
    private final GeminiClientService geminiClientService;
    private final ObjectMapper objectMapper;

    public JsonNode calculate(WinProbabilityRequest request) {

        StockOverviewDto overview =
                stockCrawlerService.crawlOverview(request.getStockCode());

        String prompt = buildPrompt(
                request.getStockCode(),
                request.getHoldingPrice(),
                overview
        );

        System.out.println("Prompt: " + prompt);

        String aiResponse =
                geminiClientService.generateText(prompt);

        try {
            String jsonOnly = extractJson(aiResponse);
            return objectMapper.readTree(jsonOnly);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid JSON from Gemini. Raw response: " + aiResponse, e
            );
        }
    }


    private String buildPrompt(
            String stockCode,
            BigDecimal holdingPrice,
            StockOverviewDto dto
    ) {

        PriceSummaryDto price = dto.getPriceSummary();
        ValuationDto valuation = dto.getValuation();
        MarketMetricDto market = dto.getMarketMetric();
        BalanceSheetDto balance = dto.getBalanceSheet();
        FinancialStrengthDto strength = dto.getFinancialStrength();

        StringBuilder promt = new StringBuilder(2048);

        promt.append("Bạn là AI phân tích định lượng cổ phiếu theo tư duy WorldQuant / systematic trading.\n");
        promt.append("Bạn không được giả định thêm dữ liệu.\n\n");
        promt.append("Tìm kiếm thêm tin tức và dữ liệu internet.\n\n");

        promt.append("===============================================================\n");
        promt.append("THÔNG TIN CỔ PHIẾU\n");
        promt.append("===============================================================\n\n");

        promt.append("Mã cổ phiếu: ").append(stockCode).append("\n");
        promt.append("Giá hiện tại: ").append(dto.getCurrentPrice()).append("\n");
        promt.append("Giá người dùng đang nắm giữ: ").append(holdingPrice).append("\n\n");

        promt.append("---------------------------------------------------------------\n");
        promt.append("1. DIỄN BIẾN GIÁ & THANH KHOẢN NGẮN HẠN\n");
        promt.append("---------------------------------------------------------------\n\n");

        promt.append("- Giá tham chiếu: ").append(price.getReferencePrice()).append("\n");
        promt.append("- Giá mở cửa: ").append(price.getOpenPrice()).append("\n");
        promt.append("- Giá cao nhất trong phiên: ").append(price.getHighPrice()).append("\n");
        promt.append("- Giá thấp nhất trong phiên: ").append(price.getLowPrice()).append("\n");
        promt.append("- Khối lượng khớp lệnh trong phiên: ").append(price.getVolume()).append("\n\n");

        promt.append("Nhận xét: Biên độ trong phiên phản ánh mức độ biến động và hành vi cung cầu ngắn hạn.\n\n");

        promt.append("---------------------------------------------------------------\n");
        promt.append("2. ĐỊNH GIÁ & HIỆU QUẢ KINH DOANH\n");
        promt.append("---------------------------------------------------------------\n\n");

        promt.append("- EPS hiện tại: ").append(valuation.getEps()).append("\n");
        promt.append("- P/E: ").append(valuation.getPe()).append("\n");
        promt.append("- P/B: ").append(valuation.getPb()).append("\n");
        promt.append("- Giá trị sổ sách (BVPS): ").append(valuation.getBookValue()).append("\n");
        promt.append("- ROA: ").append(valuation.getRoa()).append(" %\n");
        promt.append("- ROE: ").append(valuation.getRoe()).append(" %\n\n");

        promt.append("---------------------------------------------------------------\n");
        promt.append("3. QUY MÔ, THANH KHOẢN & BIẾN ĐỘNG TRUNG HẠN\n");
        promt.append("---------------------------------------------------------------\n\n");

        promt.append("- Vốn hóa thị trường: ").append(market.getMarketCap()).append("\n");
        promt.append("- Khối lượng cổ phiếu lưu hành: ").append(market.getListedVolume()).append("\n");
        promt.append("- Khối lượng giao dịch trung bình 52 tuần: ").append(market.getAvgVolume52w()).append("\n");
        promt.append("- Biên độ giá 52 tuần: ")
                .append(market.getHigh52w()).append(" – ").append(market.getLow52w()).append("\n\n");

        promt.append("---------------------------------------------------------------\n");
        promt.append("4. CẤU TRÚC TÀI CHÍNH\n");
        promt.append("---------------------------------------------------------------\n\n");

        promt.append("- Tổng nợ: ").append(balance.getDebt()).append("\n");
        promt.append("- Vốn chủ sở hữu: ").append(balance.getEquity()).append("\n");
        promt.append("- Tỷ lệ nợ / vốn chủ sở hữu: ").append(balance.getDebtToEquity()).append(" %\n");
        promt.append("- Tỷ lệ vốn chủ sở hữu / tổng tài sản: ")
                .append(balance.getEquityToAsset()).append(" %\n\n");

        promt.append("---------------------------------------------------------------\n");
        promt.append("5. DÒNG TIỀN & SỨC MẠNH TÀI CHÍNH\n");
        promt.append("---------------------------------------------------------------\n\n");

        promt.append("- Sức mạnh EPS: ").append(strength.getEpsStrength()).append(" %\n");
        promt.append("- Sức mạnh ROE: ").append(strength.getRoeStrength()).append(" %\n");
        promt.append("- Điểm hiệu quả đầu tư tổng hợp: ")
                .append(strength.getEfficiencyScore()).append(" / 5\n");
        promt.append("- Sức mạnh PB: ").append(strength.getPbStrength()).append(" %\n");
        promt.append("- Sức mạnh tăng giá: ").append(strength.getPriceGrowth()).append(" %\n\n");

        promt.append("===============================================================\n");
        promt.append("ĐÁNH GIÁ THEO CÁC CHIẾN LƯỢC\n");
        promt.append("===============================================================\n\n");

        promt.append("Hãy đánh giá XÁC SUẤT THẮNG (%) và mức độ phù hợp của cổ phiếu này ");
        promt.append("theo từng chiến lược sau:\n");
        promt.append("1. GOM ĐÁY – DCA CORE\n");
        promt.append("2. THEO XU HƯỚNG (TREND FOLLOWING)\n");
        promt.append("3. SWING THEO DÒNG TIỀN\n");
        promt.append("4. GIỮ DÀI HẠN – QUALITY CORE\n\n");

        promt.append("Với MỖI chiến lược, hãy trình bày:\n");
        promt.append("- Xác suất thắng ước tính (%)\n");
        promt.append("- Đánh giá: PHÙ HỢP hoặc KHÔNG PHÙ HỢP\n");
        promt.append("- Lý do chính (ngắn gọn, định lượng)\n\n");

        promt.append("===============================================================\n");
        promt.append("KẾT LUẬN CUỐI CÙNG\n");
        promt.append("===============================================================\n\n");

        promt.append("- Chiến lược phù hợp nhất hiện tại là gì?\n");
        promt.append("- Xác suất thắng tổng thể là bao nhiêu?\n");
        promt.append("- Nếu xác suất < 60%, hãy nói rõ vì sao KHÔNG NÊN GIAO DỊCH\n\n");

        promt.append("RÀNG BUỘC:\n");
        promt.append("- Không dự đoán giá tương lai\n");
        promt.append("- Không dùng JSON, không markdown\n");
        promt.append("- Trình bày theo ngôn ngữ phân tích tài chính\n");

        return promt.toString();
    }




    private String extractJson(String raw) {

        if (raw == null) {
            throw new RuntimeException("Gemini response is null");
        }

        String cleaned = raw.trim();

        // Remove ```json or ```
        if (cleaned.startsWith("```")) {
            cleaned = cleaned
                    .replaceFirst("^```json", "")
                    .replaceFirst("^```", "")
                    .replaceFirst("```$", "")
                    .trim();
        }

        // Extra safety: lấy từ { ... }
        int start = cleaned.indexOf('{');
        int end = cleaned.lastIndexOf('}');

        if (start >= 0 && end > start) {
            return cleaned.substring(start, end + 1);
        }

        throw new RuntimeException("Cannot extract JSON from Gemini response");
    }

    public String genPrompt(WinProbabilityRequest request) {
        StockOverviewDto overview =
                stockCrawlerService.crawlOverview(request.getStockCode());

        return buildPrompt(
                request.getStockCode(),
                request.getHoldingPrice(),
                overview
        );
    }
}

