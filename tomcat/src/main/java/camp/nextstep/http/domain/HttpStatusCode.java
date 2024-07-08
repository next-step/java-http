package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpStatusException;

import java.util.Arrays;

public enum HttpStatusCode {
    OK(200, "OK");

    private static final int MAX_STATUS_CODE = 999;
    private static final int MIN_STATUS_CODE = 100;

    private final int value;
    private final String reasonPhrase;

    HttpStatusCode(final int value, final String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public static HttpStatusCode valueOf(final int statusCode) {
        validate(statusCode);
        return Arrays.stream(HttpStatusCode.values())
                .filter(code -> code.value == statusCode)
                .findFirst()
                .orElseThrow(() -> new InvalidHttpStatusException("No matching HttpStatusCode found for " + statusCode));
    }

    private static void validate(final int statusCode) {
        if (statusCode < MIN_STATUS_CODE || statusCode > MAX_STATUS_CODE) {
            throw new InvalidHttpStatusException("HttpStatusCode must be three digit");
        }
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
