package sonnh.dev.assetsmanagement.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class ParseHelper {
    public static BigDecimal parseDecimal(String s) {
        return new BigDecimal(
                s.replace(",", "")
                        .replace("K", "000")
                        .replace("x", "")
                        .trim()
        );
    }

    public static BigDecimal parsePercent(String s) {
        return new BigDecimal(s.replace("%", "").trim());
    }

    public static Long parseLong(String s) {
        return Long.parseLong(s.replace(",", "").trim());
    }

}
