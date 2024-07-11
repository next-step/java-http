package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpHeaderException;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCookies {
    private static final String COOKIES_DELIMITER = ";";
    private static final String COOKIE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final int SPLIT_LIMIT = 2;

    private final Map<String, String> cookies;

    public HttpCookies() {
        this.cookies = new LinkedHashMap<>();
    }

    public HttpCookies(final String cookies) {
        if (cookies == null) {
            this.cookies = new LinkedHashMap<>();
            return;
        }

        this.cookies = Arrays.stream(cookies.split(COOKIES_DELIMITER))
                .map(this::splitCookie)
                .collect(Collectors.toMap(
                        header -> header[KEY_INDEX].trim(),
                        header -> header[VALUE_INDEX].trim(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    private String[] splitCookie(final String cookies) {
        if (cookies.contains(COOKIE_DELIMITER)) {
            return cookies.split(COOKIE_DELIMITER, SPLIT_LIMIT);
        }
        throw new InvalidHttpHeaderException("Invalid Http Cookie: " + cookies);
    }

    public void add(final String name, final String value) {
        cookies.put(name, value);
    }

    public String get(final String key) {
        return cookies.get(key);
    }

    public String convertToString() {
        return cookies.entrySet()
                .stream()
                .map(entry -> String.format("Set-Cookie: %s%s%s ", entry.getKey(), COOKIE_DELIMITER, entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }
}
