package sonnh.dev.assetsmanagement.utils;

import java.util.Arrays;
import java.util.Optional;
public class EnumUtils {
    /* =========================
     * PARSE
     * ========================= */

    public static <E extends Enum<E>> E fromString(
            Class<E> enumClass,
            String value
    ) {
        if (value == null || value.isBlank()) return null;

        try {
            return Enum.valueOf(enumClass, value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static <E extends Enum<E>> Optional<E> fromStringOptional(
            Class<E> enumClass,
            String value
    ) {
        return Optional.ofNullable(fromString(enumClass, value));
    }

    /* =========================
     * CHECK
     * ========================= */

    public static <E extends Enum<E>> boolean isValid(
            Class<E> enumClass,
            String value
    ) {
        if (value == null) return false;

        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }

    public static <E extends Enum<E>> boolean equals(
            E e1,
            E e2
    ) {
        return e1 != null && e1 == e2;
    }

    /* =========================
     * META
     * ========================= */

    public static <E extends Enum<E>> String[] names(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}
