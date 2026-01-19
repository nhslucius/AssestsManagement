package sonnh.dev.assetsmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class StockSnapshotDto {

    private String code;                 // HPG
    private String title;                // Trang tiêu đề
    private Map<String, String> metrics; // Toàn bộ chỉ số crawl được
    private LocalDateTime crawledAt;
}
