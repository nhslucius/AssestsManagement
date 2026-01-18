package sonnh.dev.assetsmanagement.utils;

import java.util.Collection;

public final class StringUtils {

    private StringUtils() {
    }

    /* =========================
     * BASIC
     * ========================= */

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    public static String defaultIfBlank(String s, String defaultVal) {
        return isBlank(s) ? defaultVal : s;
    }

    /* =========================
     * CONVERT
     * ========================= */

    public static String upper(String s) {
        return s == null ? null : s.toUpperCase();
    }

    public static String lower(String s) {
        return s == null ? null : s.toLowerCase();
    }

    /* =========================
     * SEARCH
     * ========================= */

    public static boolean containsIgnoreCase(String source, String search) {
        if (source == null || search == null) return false;
        return source.toLowerCase().contains(search.toLowerCase());
    }

    /* =========================
     * JOIN
     * ========================= */

    public static String join(
            Collection<?> items,
            String delimiter
    ) {
        if (items == null || items.isEmpty()) return "";

        return items.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + delimiter + b)
                .orElse("");
    }
}