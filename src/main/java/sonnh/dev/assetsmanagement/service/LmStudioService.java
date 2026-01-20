package sonnh.dev.assetsmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    @Value("${lmstudio.base-url}")
    private String baseUrl;

    @Value("${lmstudio.model}")
    private String model;

    public String sendPrompt(WinProbabilityRequest request) {

        String url = baseUrl + "/chat/completions";
        String prompt = this.buildPrompt2(request.getStockCode(), request.getHoldingPrice());
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

}
