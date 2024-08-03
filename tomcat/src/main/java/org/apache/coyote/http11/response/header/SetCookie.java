package org.apache.coyote.http11.response.header;

import java.util.Map;
import java.util.stream.Collectors;

public class SetCookie {

    public final Map<String, String> cookies;

    public SetCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void addCookie(String key, String value){
        cookies.put(key, value);
    }
    @Override
    public String toString() {
        return String.format("Set-Cookie: %s ", cookies.keySet().stream()
                .map(key -> key + "=" + cookies.get(key))
                .collect(Collectors.joining(";")));

    }
}
