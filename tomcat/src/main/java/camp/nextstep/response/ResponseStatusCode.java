package camp.nextstep.response;

public enum ResponseStatusCode {
    OK("200 OK"),
    Found("302 Found"),
    NotFound("404 Not Found"),
    ;

    private final String responseMessage;

    ResponseStatusCode(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
