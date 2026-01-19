package sonnh.dev.assetsmanagement.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.dto.StockOverviewDto;
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
            StockOverviewDto overview
    ) {
        try {
            String overviewJson = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(overview);

            return """
                    Bạn là AI phân tích định lượng cổ phiếu theo tư duy WorldQuant / StatArb.
                    Bạn chỉ được sử dụng DỮ LIỆU ĐƯỢC CUNG CẤP BÊN DƯỚI, KHÔNG ĐƯỢC GIẢ ĐỊNH.
                    Lấy dữ liệu mới nhất từ internet về cổ phiếu theo ngày hiện tại
                    ========================
                    CHIẾN LƯỢC & MỤC TIÊU
                    ========================
                    
                    - Mục tiêu: chỉ tham gia khi xác suất thắng > 60%
                    - Phong cách: DCA + gom đáy thông minh
                    - Không dự đoán giá tương lai
                    - Không đưa lời khuyên cảm tính
                    
                    ========================
                    DỮ LIỆU CỔ PHIẾU (StockOverviewDto)
                    ========================
                    
                    stock_code = """ + stockCode + """
                    
                    """ + overviewJson + """
                    
                    ========================
                    GIÁ NGƯỜI DÙNG ĐANG NẮM GIỮ
                    ========================
                    
                    holding_price = """ + holdingPrice + """
                    
                    ========================
                    ALPHA FACTORS BẮT BUỘC ÁP DỤNG
                    ========================
                    
                    1. Financial Quality (40%)
                    2. Money Flow (35%)
                    3. Price Momentum (25%)
                    
                    ========================
                    OUTPUT: JSON ONLY (NO MARKDOWN)
                    trả lời bằng tiếng việt
                    ========================
                    """;

        } catch (Exception e) {
            throw new RuntimeException("Failed to build Gemini prompt", e);
        }
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
}

