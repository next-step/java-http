package org.apache.coyote.http11.response.header;

import java.util.Map;

public class SetCookieAdd implements SetCookieConsumer<Http11ResponseHeader> {

    public static final String JSESSIONID = "JSESSIONID";

    @Override
    public void add(Http11ResponseHeader header, Map<String, String> session) {
        if (session.containsKey(JSESSIONID)) {
            header.addCookie(JSESSIONID, session.get(JSESSIONID));
        }

    }
}
