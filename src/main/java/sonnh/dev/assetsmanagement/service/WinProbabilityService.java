package sonnh.dev.assetsmanagement.service;

import org.springframework.stereotype.Service;
/*
5️⃣ WinProbabilityService – TỔNG HỢP EDGE
Service này KHÔNG phải “máy dự đoán”

Nó không nói:

“70% chắc thắng”

Mà nói:

“Với các giả định hiện tại, đây là trade có lợi thế”

Vì sao dùng trọng số cố định?

Thesis: nền tảng (30%)

Valuation + Technical: xác suất thực thi (50%)

Risk/Reward: hiệu quả vốn (20%)

📌 Trọng số này có thể:

Thay đổi theo chu kỳ

Thay đổi theo phong cách quỹ
 */
@Service
public class WinProbabilityService {

    private static final double W_THESIS = 0.30;
    private static final double W_VALUATION = 0.25;
    private static final double W_TECHNICAL = 0.25;
    private static final double W_RR = 0.20;

    public double calculate(double thesis,
                            double valuation,
                            double technical,
                            double rr) {

        return thesis * W_THESIS
                + valuation * W_VALUATION
                + technical * W_TECHNICAL
                + rr * W_RR;
    }
}

