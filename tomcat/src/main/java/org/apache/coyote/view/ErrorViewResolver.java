package org.apache.coyote.view;

public class ErrorViewResolver {

    public static final String ERROR_MESSAGE = "ERROR: ";

    public static String errorView(final String message) {
        return ERROR_MESSAGE + message;
    }
}
