package org.apache.coyote.http11.constants;

public final class HttpFormat {

    private HttpFormat() {
    }

    public static final String SPACE = " ";
    public static final String CRLF = "\r\n";

    public static String headerFieldValue(String key, String value) {
        return key + HEADERS.FIELD_VALUE_DELIMITER + value + SPACE + CRLF;

    }
    public static class HEADERS {
        public static final String FIELD_VALUE_DELIMITER = ": ";
        public static final String CONTENT_LENGTH = "Content-Length";
        public static final String COOKIE_RESPONSE_HEADER_FIELD = "Set-Cookie";
        public static final String COOKIE_REQUEST_HEADER_FIELD = "Cookie";
        public static final String HOST = "Host";
        public static final String LOCATION = "Location";
        public static final String CONTENT_TYPE = "Content-Type";
    }
}
