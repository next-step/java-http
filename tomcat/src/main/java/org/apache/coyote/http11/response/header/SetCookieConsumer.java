package org.apache.coyote.http11.response.header;

import java.util.Map;

@FunctionalInterface
public interface SetCookieConsumer<T> {
    void add(T obj, Map<String, String> session);

}
