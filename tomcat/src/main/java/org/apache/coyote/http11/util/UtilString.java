package org.apache.coyote.http11.util;

public class UtilString {
    public static String parseExtension(final String context) {
        return context.substring(context.lastIndexOf("."));
    }
}
