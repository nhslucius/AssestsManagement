package sonnh.dev.assetsmanagement.service;

import org.springframework.stereotype.Service;
import sonnh.dev.assetsmanagement.request.StockEvaluationRequest;

/*
2️⃣ ValuationService – ĐỊNH GIÁ & BIÊN AN TOÀN
Nghiệp vụ cốt lõi

Service này trả lời câu hỏi:

“Tại mức giá hiện tại, ta có đang mua rẻ hơn giá trị hợp lý không?”

Không phải tìm “giá chính xác”, mà tìm margin of safety.

2.1 Relative Valuation (định giá tương đối)
Ý nghĩa nghiệp vụ

So sánh:

Với chính quá khứ doanh nghiệp

Với doanh nghiệp cùng chu kỳ

📌 Dùng để trả lời:

“Thị trường đang trả premium hay discount cho cổ phiếu này?”

Vì sao không cho điểm tuyệt đối?

Vì:

P/E thấp chưa chắc rẻ (đang đỉnh chu kỳ)

P/E cao chưa chắc đắt (đang đáy lợi nhuận)

➡️ Chỉ dùng để xếp hạng tương đối, không làm trung tâm.

2.2 Earnings-based Valuation (mid-cycle earnings)
Đây là phần QUAN TRỌNG NHẤT

Service này mô phỏng cách quỹ định giá:

❌ Không dùng EPS đáy
❌ Không dùng EPS đỉnh
✅ Dùng EPS mid-cycle

📌 Câu hỏi nghiệp vụ:

“Trong trạng thái bình thường của chu kỳ, doanh nghiệp đáng giá bao nhiêu?”

Fair value dùng để làm gì?

Không phải để dự đoán giá chính xác, mà để:

Tính upside/downside

Đánh giá biên an toàn

2.3 Consensus Check
Ý nghĩa thực tế

Consensus không phải để tin, mà để:

Tránh lệch pha hoàn toàn với thị trường

Biết kỳ vọng chung đang ở đâu

📌 Quỹ KHÔNG mua vì consensus cao
📌 Nhưng cũng cảnh giác nếu mình quá khác số đông

Output của ValuationService

➡️ Một con số thể hiện:

“Định giá hiện tại có tạo edge hay không?”
 */
@Service
public class ValuationService {

    public double calculateValuationEdge(StockEvaluationRequest.ValuationInput v) {

        double relativeScore = relativeValuationScore(v);
        double earningsScore = earningsValuationScore(v);
        double consensusScore = consensusScore(v);

        return (relativeScore + earningsScore + consensusScore) / 3.0;
    }

    private double relativeValuationScore(StockEvaluationRequest.ValuationInput v) {
        if (v.getPeForward() < v.getPeHistoricalAvg()
                && v.getPeForward() < v.getPeSectorAvg()) {
            return 0.7;
        }
        return 0.4;
    }

    private double earningsValuationScore(StockEvaluationRequest.ValuationInput v) {
        double fairValue = v.getEpsMidCycle() * v.getTargetPe();
        double upside = (fairValue - v.getCurrentPrice()) / v.getCurrentPrice();

        if (upside > 0.3) return 0.8;
        if (upside > 0.15) return 0.6;
        return 0.4;
    }

    private double consensusScore(StockEvaluationRequest.ValuationInput v) {
        double upside = (v.getConsensusTargetPrice() - v.getCurrentPrice())
                / v.getCurrentPrice();

        if (upside > 0.25) return 0.7;
        if (upside > 0.15) return 0.5;
        return 0.3;
    }
}

