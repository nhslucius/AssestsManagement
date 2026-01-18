package sonnh.dev.assetsmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockSnapshot {

    private String symbol;

    private Double referencePrice;   // Tham chiếu
    private Double openPrice;        // Mở cửa
    private Double lowPrice;         // Thấp
    private Double highPrice;        // Cao
    private Double closePrice;       // Giá hiện tại (C)

    private Long volume;             // Khối lượng
    private Double tradedValue;      // Giá trị (tỷ)
    private Long avgVolume10d;        // KLTB 10 ngày

    private Double beta;
    private Double marketCap;        // Thị giá vốn (tỷ)
    private Long sharesOutstanding;  // Số lượng CPLH

    private Double pe;
    private Double eps;
}


