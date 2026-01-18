package sonnh.dev.assetsmanagement.service;

import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.request.StockEvaluationRequest;
/*
3️⃣ TechnicalService – TIMING & DÒNG TIỀN
Nghiệp vụ service này KHÔNG làm gì?

❌ Không dự đoán đỉnh đáy
❌ Không tạo tín hiệu mua/bán máy móc
❌ Không dùng để override thesis

Service này dùng để làm gì?

Trả lời câu hỏi:

“Ở thời điểm hiện tại, dòng tiền có ủng hộ việc vào vị thế không?”

3.1 Market structure (Accumulation)

📌 Quỹ không mua khi đang phân phối

Tích lũy =

Vol không đột biến

Giá không bị xả mạnh

Cầu hấp thụ tốt

➡️ Đây là điều kiện bắt buộc, không phải cộng điểm cho vui.

3.2 OBV / Volume – Proxy dòng tiền tổ chức

Vì:

Không thấy được lệnh tổ chức

Nên phải nhìn hệ quả để lại

📌 OBV không gãy = tổ chức chưa rút

3.3 RSI / MACD – Momentum, không phải đỉnh đáy

RSI 50–65:

Giá đang được chấp nhận

Không quá nóng

Không bị phân phối

➡️ Timing tốt hơn rất nhiều so với RSI > 70.

Bản chất TechnicalService

👉 Điều chỉnh xác suất theo thời điểm,
không điều chỉnh chất lượng doanh nghiệp.

4️⃣ RiskRewardService – BỘ LỌC SỐNG CÒN
Đây là service QUAN TRỌNG NHẤT

Câu hỏi nghiệp vụ duy nhất:

“Nếu sai, tôi mất bao nhiêu? Nếu đúng, tôi được bao nhiêu?”

Vì sao RR có thể override win rate?

Trong thực tế quỹ:

Win rate thấp nhưng RR cao → vẫn vào

Win rate cao nhưng RR xấu → không vào

📌 Đây là tư duy expected value, không phải cảm xúc.

Cách service này được dùng

RR < 1:2 → tự động bóp xác suất

RR ≥ 1:3 → cho phép:

Thesis chưa hoàn hảo

Technical chưa đẹp

➡️ RR bảo vệ tài khoản, không bảo vệ cái tôi.
 */
@Service
public class TechnicalService { //RSI/MACD để đo momentum & timing, không bắt đỉnh đáy.

    public double score(StockEvaluationRequest.TechnicalInput t) {

        double score = 0;

        if (t.isAccumulationPhase()) score += 0.3;
        if (t.isObvUptrend()) score += 0.25;
        if (t.isMacdPositive()) score += 0.2;
        if (t.getRsi() > 45 && t.getRsi() < 65) score += 0.25;

        return Math.min(score, 1.0);
    }
}

