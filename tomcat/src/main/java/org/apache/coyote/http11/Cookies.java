package org.apache.coyote.http11;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Cookies implements Iterable<Cookie> {
    private static final String COOKIE_SEPARATOR = ";";

    private final List<Cookie> values;

    private Cookies(final List<Cookie> values) {
        this.values = values;
    }

    public static Cookies from(final String cookies) {
        List<Cookie> values = Arrays.stream(cookies.split(COOKIE_SEPARATOR))
                .filter(Predicate.not(String::isBlank))
                .map(Cookie::from)
                .toList();

        return new Cookies(values);
    }

    public Stream<Cookie> stream() {
        return values.stream();
    }

    @Override
    public Iterator<Cookie> iterator() {
        return values.iterator();
    }
}
