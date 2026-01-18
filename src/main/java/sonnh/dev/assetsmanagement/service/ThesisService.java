package sonnh.dev.assetsmanagement.service;

import org.springframework.stereotype.Service;

/*
1️⃣ ThesisService – XÁC SUẤT ĐÚNG LUẬN ĐIỂM
Nghiệp vụ service này làm gì?

ThesisService đại diện cho “xác suất nền” của khoản đầu tư.

Câu hỏi mà service này trả lời không phải:

“Giá có tăng không?”

Mà là:

“Luận điểm đầu tư ban đầu còn đúng hay đã gãy?”

Trong thực tế quỹ sử dụng như thế nào?

Ở buy-side, thesis luôn do con người đánh giá, không giao cho máy:

Ví dụ với HPG:

Chu kỳ thép: đang hồi phục hay chưa?

Đầu tư công: có thật sự giải ngân?

Công suất Dung Quất 2: đã phản ánh vào sản lượng?

👉 Analyst sẽ đưa ra một con số định tính:

0.8 → thesis rất rõ

0.5 → thesis trung tính

<0.3 → thesis yếu / sắp gãy

Vì sao service này KHÔNG tự tính?

Vì:

Thesis là forward-looking

Nhiều yếu tố không có dữ liệu số hóa

Máy chỉ chuẩn hóa & khóa biên độ

📌 Nếu thesis = 0 → toàn bộ win probability = 0, đúng tư duy quỹ:

“Sai luận điểm thì kỹ thuật đẹp đến mấy cũng không mua.”

 */
@Service
public class ThesisService {

    public double score(double thesisScore) {
        return Math.max(0, Math.min(thesisScore, 1));
    }
}

