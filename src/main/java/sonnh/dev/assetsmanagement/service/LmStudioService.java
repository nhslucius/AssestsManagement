package sonnh.dev.assetsmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sonnh.dev.assetsmanagement.dto.*;
import sonnh.dev.assetsmanagement.request.LmStudioChatRequest;
import sonnh.dev.assetsmanagement.request.WinProbabilityRequest;
import sonnh.dev.assetsmanagement.response.LmStudioChatResponse;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LmStudioService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final StockCrawlerService stockCrawlerService;

    @Value("${lmstudio.base-url}")
    private String baseUrl;

    @Value("${lmstudio.model}")
    private String model;

    public String sendPrompt(WinProbabilityRequest request) {

        String url = baseUrl + "/chat/completions";
        StockOverviewDto overview =
                stockCrawlerService.crawlOverview(request.getStockCode());
        String prompt = this.buildPrompt(request.getStockCode(), request.getHoldingPrice(), overview);
        String cleanPrompt = sanitizePrompt(prompt);
        LmStudioChatRequest lmRequest = new LmStudioChatRequest();
        lmRequest.setModel(model);
        lmRequest.setMessages(List.of(
                new LmStudioChatRequest.Message("system",
                        "You are a professional financial analyst."),
                new LmStudioChatRequest.Message("user", prompt)
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("sendPrompt LM studio chat request: " + prompt);
        HttpEntity<LmStudioChatRequest> entity =
                new HttpEntity<>(lmRequest, headers);

        ResponseEntity<LmStudioChatResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        LmStudioChatResponse.class
                );

        if (response.getBody() == null
                || response.getBody().getChoices() == null
                || response.getBody().getChoices().isEmpty()) {
            throw new RuntimeException("Empty response from LM Studio");
        }

        return response.getBody()
                .getChoices()
                .get(0)
                .getMessage()
                .getContent();
    }

    private String sanitizePrompt(String prompt) {
        if (prompt == null) {
            return null;
        }
        return prompt
                .replace("\r\n", "\n")   // Windows → Unix
                .replace("\r", "\n");    // CR còn sót
    }


    private String buildPrompt2(
            String stockCode,
            BigDecimal holdingPrice
    ) {

        StringBuilder prompt = new StringBuilder(4096);

        prompt.append("Bạn là AI phân tích định lượng cổ phiếu theo tư duy WorldQuant / systematic trading.\n");
        prompt.append("Bạn KHÔNG được giả định thêm dữ liệu và KHÔNG dự đoán giá tương lai.\n");
        prompt.append("Mọi kết luận phải dựa trên dữ liệu thực tế bạn thu thập được.\n\n");

        prompt.append("NGUỒN DỮ LIỆU BẮT BUỘC\n");
        prompt.append("- Thông tin tổng quan cổ phiếu: https://simplize.vn/co-phieu/")
                .append(stockCode.toUpperCase()).append("\n");
        prompt.append("- Dữ liệu tài chính và chỉ số tài chính mới nhất từ internet\n\n");

        prompt.append("===============================================================\n");
        prompt.append("THÔNG TIN CỔ PHIẾU\n");
        prompt.append("===============================================================\n\n");

        prompt.append("Mã cổ phiếu: ").append(stockCode.toUpperCase()).append("\n");
        prompt.append("Giá người dùng mua vào: ").append(holdingPrice).append("\n\n");

        prompt.append("===============================================================\n");
        prompt.append("PHONG CÁCH & CHIẾN LƯỢC GIAO DỊCH CẦN ĐÁNH GIÁ\n");
        prompt.append("===============================================================\n\n");

        prompt.append("1. BOTTOM_FISHING_DCA (GOM ĐÁY – DCA CORE)\n");
        prompt.append("   - Doanh nghiệp có mức nợ thấp, đòn bẩy tài chính không quá cao\n");
        prompt.append("   - Dòng tiền có dấu hiệu tích lũy (khối lượng ổn định hoặc tăng dần khi giá không tăng mạnh)\n");
        prompt.append("   - Giá chưa tăng mạnh, còn ở vùng chiết khấu so với lịch sử\n\n");

        prompt.append("2. TREND_FOLLOWING (THEO XU HƯỚNG)\n");
        prompt.append("   - Giá vượt các vùng kháng cự ngắn hạn hoặc trung hạn\n");
        prompt.append("   - Khối lượng giao dịch tăng xác nhận xu hướng\n");
        prompt.append("   - Động lượng giá dương, hạn chế rủi ro mua đuổi\n\n");

        prompt.append("3. SWING_MONEY_FLOW (SWING THEO DÒNG TIỀN)\n");
        prompt.append("   - Xuất hiện các phiên volume spike bất thường\n");
        prompt.append("   - Chỉ báo OBV hoặc các chỉ báo dòng tiền cho thấy xu hướng tăng nhanh\n");
        prompt.append("   - Biến động giá ngắn hạn đủ lớn để khai thác swing\n\n");

        prompt.append("4. LONG_TERM_QUALITY (GIỮ DÀI HẠN – QUALITY CORE)\n");
        prompt.append("   - Nền tảng tài chính ổn định qua nhiều năm\n");
        prompt.append("   - ROE / ROA duy trì ở mức tốt so với mặt bằng ngành\n");
        prompt.append("   - Mô hình kinh doanh ít phụ thuộc vào timing thị trường\n\n");

        prompt.append("===============================================================\n");
        prompt.append("YÊU CẦU PHÂN TÍCH\n");
        prompt.append("===============================================================\n\n");

        prompt.append("VỚI MỖI CHIẾN LƯỢC, hãy thực hiện đầy đủ:\n");
        prompt.append("- Đánh giá mức độ đáp ứng các điều kiện của chiến lược\n");
        prompt.append("- Ước tính XÁC SUẤT THẮNG (%) nếu áp dụng chiến lược đó\n");
        prompt.append("- Kết luận: PHÙ HỢP hoặc KHÔNG PHÙ HỢP\n");
        prompt.append("- Lý do chính, mang tính định lượng (không chung chung)\n\n");

        prompt.append("===============================================================\n");
        prompt.append("KẾT LUẬN CUỐI CÙNG\n");
        prompt.append("===============================================================\n\n");

        prompt.append("- Giá hiện tại của cổ phiếu tại thời điểm dữ liệu\n");
        prompt.append("- Chiến lược phù hợp nhất hiện tại là gì?\n");
        prompt.append("- Xác suất thắng tổng thể (dựa trên chiến lược phù hợp nhất)\n");
        prompt.append("- Nếu xác suất < 60%, hãy giải thích rõ vì sao rủi ro vẫn cao\n");
        prompt.append("- Để cải thiện xác suất thắng lên >= 70%:\n");
        prompt.append("  + Nên mua ở vùng giá nào để có biên an toàn tốt hơn\n");
        prompt.append("  + Những điều kiện nào cần thay đổi (giá, dòng tiền, tài chính)\n\n");

        prompt.append("RÀNG BUỘC:\n");
        prompt.append("- Không dự đoán giá tương lai\n");
        prompt.append("- Không khuyến nghị cảm tính\n");
        prompt.append("- Trình bày như một báo cáo phân tích tài chính chuyên nghiệp\n");
        prompt.append("Sau khi hoàn tất phân tích, hãy đưa ra KẾT LUẬN CUỐI CÙNG cho người dùng.\n" +
                "KHÔNG hiển thị quá trình suy nghĩ nội bộ.\n" +
                "CHỈ trả về câu trả lời cuối cùng, đầy đủ và rõ ràng.\n");
        return prompt.toString().replace("\n"," ");
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
//        promt.append("Tìm kiếm thêm tin tức và dữ liệu internet.\n\n");

        promt.append("===============================================================\n");
        promt.append("THÔNG TIN CỔ PHIẾU\n");
        promt.append("===============================================================\n\n");

        promt.append("Mã cổ phiếu: ").append(stockCode).append("\n");
        promt.append("Tên công ty ").append(dto.getCompanyName()).append("\n");
        promt.append("Giá hiện tại: ").append(dto.getCurrentPrice()).append("\n");
        promt.append("Giá người dùng mua vào: ").append(holdingPrice).append("\n\n");

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
        promt.append("- Nên mua vùng giá bao nhiêu để cải thiện tỉ lệ thắng lên >= 70% \n\n");

        promt.append("RÀNG BUỘC:\n");
        promt.append("- Không dự đoán giá tương lai\n");
        promt.append("- Trình bày theo ngôn ngữ phân tích tài chính\n");
        promt.append("Sau khi hoàn tất phân tích, hãy đưa ra KẾT LUẬN CUỐI CÙNG cho người dùng.\n" +
                "KHÔNG hiển thị quá trình suy nghĩ nội bộ.\n" +
                "CHỈ trả về câu trả lời cuối cùng, đầy đủ và rõ ràng.\n");
        return promt.toString();
    }
}
