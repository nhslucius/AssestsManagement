package sonnh.dev.assetsmanagement.utils;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DateUtil {

    /* =========================
     * COMMON FORMATTERS
     * ========================= */

    public static final DateTimeFormatter ISO_DATE =
            DateTimeFormatter.ISO_LOCAL_DATE;               // yyyy-MM-dd

    public static final DateTimeFormatter ISO_DATE_TIME =
            DateTimeFormatter.ISO_LOCAL_DATE_TIME;          // yyyy-MM-ddTHH:mm:ss

    public static final DateTimeFormatter VN_DATE =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final DateTimeFormatter VN_DATE_TIME =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final List<DateTimeFormatter> SUPPORTED_DATE_FORMATS = List.of(
            ISO_DATE,
            VN_DATE
    );

    private static final List<DateTimeFormatter> SUPPORTED_DATE_TIME_FORMATS = List.of(
            ISO_DATE_TIME,
            VN_DATE_TIME
    );

    /* =========================
     * PARSE
     * ========================= */

    public static LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) return null;

        for (DateTimeFormatter f : SUPPORTED_DATE_FORMATS) {
            try {
                return LocalDate.parse(value, f);
            } catch (DateTimeParseException ignored) {}
        }
        throw new IllegalArgumentException("Unsupported date format: " + value);
    }

    public static LocalDateTime parseDateTime(String value) {
        if (value == null || value.isBlank()) return null;

        for (DateTimeFormatter f : SUPPORTED_DATE_TIME_FORMATS) {
            try {
                return LocalDateTime.parse(value, f);
            } catch (DateTimeParseException ignored) {}
        }
        throw new IllegalArgumentException("Unsupported datetime format: " + value);
    }

    /* =========================
     * FORMAT
     * ========================= */

    public static String format(LocalDate date, DateTimeFormatter formatter) {
        return date == null ? null : date.format(formatter);
    }

    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        return dateTime == null ? null : dateTime.format(formatter);
    }

    /* =========================
     * BETWEEN / COMPARE
     * ========================= */

    public static boolean isBetween(LocalDate target, LocalDate from, LocalDate to) {
        if (target == null) return false;

        if (from != null && target.isBefore(from)) return false;
        if (to != null && target.isAfter(to)) return false;

        return true;
    }

    public static boolean isBetween(LocalDateTime target,
                                    LocalDateTime from,
                                    LocalDateTime to) {
        if (target == null) return false;

        if (from != null && target.isBefore(from)) return false;
        if (to != null && target.isAfter(to)) return false;

        return true;
    }

    public static boolean isBefore(LocalDate a, LocalDate b) {
        return a != null && b != null && a.isBefore(b);
    }

    public static boolean isAfter(LocalDate a, LocalDate b) {
        return a != null && b != null && a.isAfter(b);
    }

    /* =========================
     * START / END OF
     * ========================= */

    public static LocalDateTime startOfDay(LocalDate date) {
        return date == null ? null : date.atStartOfDay();
    }

    public static LocalDateTime endOfDay(LocalDate date) {
        return date == null ? null :
                date.atTime(LocalTime.MAX);
    }

    public static LocalDate startOfMonth(LocalDate date) {
        return date == null ? null :
                date.withDayOfMonth(1);
    }

    public static LocalDate endOfMonth(LocalDate date) {
        return date == null ? null :
                date.withDayOfMonth(date.lengthOfMonth());
    }

    public static LocalDate startOfYear(int year) {
        return LocalDate.of(year, 1, 1);
    }

    public static LocalDate endOfYear(int year) {
        return LocalDate.of(year, 12, 31);
    }

    /* =========================
     * EXTRACT
     * ========================= */

    public static int getYear(LocalDate date) {
        return date == null ? 0 : date.getYear();
    }

    public static int getMonth(LocalDate date) {
        return date == null ? 0 : date.getMonthValue();
    }

    public static int getDay(LocalDate date) {
        return date == null ? 0 : date.getDayOfMonth();
    }

    /* =========================
     * CALCULATION
     * ========================= */

    public static long daysBetween(LocalDate from, LocalDate to) {
        if (from == null || to == null) return 0;
        return ChronoUnit.DAYS.between(from, to);
    }

    public static long monthsBetween(LocalDate from, LocalDate to) {
        if (from == null || to == null) return 0;
        return ChronoUnit.MONTHS.between(from, to);
    }

    public static long yearsBetween(LocalDate from, LocalDate to) {
        if (from == null || to == null) return 0;
        return ChronoUnit.YEARS.between(from, to);
    }

    /* =========================
     * NOW
     * ========================= */

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /* =========================
     * ZONE CONVERT
     * ========================= */

    public static LocalDateTime toLocalDateTime(Instant instant, ZoneId zone) {
        return instant == null ? null :
                LocalDateTime.ofInstant(instant, zone);
    }

    public static Instant toInstant(LocalDateTime dateTime, ZoneId zone) {
        return dateTime == null ? null :
                dateTime.atZone(zone).toInstant();
    }
}
