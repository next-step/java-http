package camp.nextstep.util;

public class EnumUtil {
    public static <T extends Enum<?>> T searchEnum(
            Class<T> enumeration,
            String search
    ) {
        for (T each : enumeration.getEnumConstants()) {
            if (each.name().compareToIgnoreCase(search) == 0) {
                return each;
            }
        }
        return null;
    }
}
