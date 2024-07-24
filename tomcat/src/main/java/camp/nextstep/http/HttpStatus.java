package camp.nextstep.http;

public enum HttpStatus {

  OK(200, "OK"),
  FOUND(302, "Found"),
  UNAUTHORIZED(401, "Unauthorized"),
  NOT_FOUND(404, "Not Found"),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
  ;
  private final int statusCode;

  private final String reasonPhrase;

  public int getStatusCode() {
    return statusCode;
  }

  public String getReasonPhrase() {
    return reasonPhrase;
  }

  HttpStatus(int statusCode, String reasonPhrase) {
    this.statusCode = statusCode;
    this.reasonPhrase = reasonPhrase;
  }


}
