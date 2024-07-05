package org.apache.coyote.http11;

public class StringTokenizer {

    public static String[] token(final String str, final String delimiter) {
        return str.split(delimiter);
    }
}
