package org.apache.file;

public record MediaType(String type) {
    public static final MediaType TEXT_HTML = new MediaType("text/html");
    public static final MediaType FORM_URL_ENCODED = new MediaType("application/x-www-form-urlencoded");

    @Override
    public String toString() {
        return type;
    }
}
