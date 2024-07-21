package camp.nextstep.http.enums;

public enum HttpStatus {
    OK("200", "OK"),
    FOUND("302", "FOUND"),
    BAD_REQUEST("401", "BAD REQUEST"),
    NOT_FOUND("404", "NOT FOUND"),
    INTERNAL_SERVER_ERROR("500", "INTERNAL SERVER ERROR");


    private String statusCode;
    private String message;

    HttpStatus(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
