package sonnh.dev.assetsmanagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ValuationDto {
    private BigDecimal bookValue; // Giá sổ sách
    private BigDecimal eps;
    private BigDecimal pe;
    private BigDecimal pb;
    private BigDecimal roa; // %
    private BigDecimal roe; // %
}
