package sonnh.dev.assetsmanagement.utils;

public final class NumberUtils {

    /* =========================
     * PARSE
     * ========================= */

    public static Integer toInteger(String value) {
        try {
            return value == null ? null : Integer.valueOf(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Long toLong(String value) {
        try {
            return value == null ? null : Long.valueOf(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Double toDouble(String value) {
        try {
            return value == null ? null : Double.valueOf(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /* =========================
     * CHECK
     * ========================= */

    public static boolean isPositive(Number n) {
        return n != null && n.doubleValue() > 0;
    }

    public static boolean isZeroOrPositive(Number n) {
        return n != null && n.doubleValue() >= 0;
    }

    public static boolean between(
            Number value,
            Number min,
            Number max
    ) {
        if (value == null) return false;

        double v = value.doubleValue();
        if (min != null && v < min.doubleValue()) return false;
        if (max != null && v > max.doubleValue()) return false;

        return true;
    }

    /* =========================
     * DEFAULT
     * ========================= */

    public static int defaultIfNull(Integer value, int defaultVal) {
        return value == null ? defaultVal : value;
    }

    public static long defaultIfNull(Long value, long defaultVal) {
        return value == null ? defaultVal : value;
    }
}
