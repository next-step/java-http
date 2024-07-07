package org.apache.coyote.http11;

public final class StringTokenizer {

    private StringTokenizer() {
    }

    public static String[] token(final String str, final String delimiter) {
        return str.split(delimiter);
    }
}
