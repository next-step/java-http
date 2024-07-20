package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public final class MapUtils {

    private MapUtils() {
    }

    public static<T> Map<String, T> parseKeyValuePair(String delimiter, String[] lines) {
        return Arrays.stream(lines)
                .map(pair -> pair.split(delimiter))
                .map(pair -> Map.entry(pair[0], pair[1]))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (T) entry.getValue()));
    }
}
