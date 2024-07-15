package camp.nextstep.http;

import camp.nextstep.exception.UnsupportedContentType;

public enum ContentType {
  TEXT_HTML("html", "text/html"),
  TEXT_CSS("css", "text/css"),
  APPLICATION_JS("js", "application/javascript");
  private final String extention;
  private final String type;

  ContentType(String extention, String type) {
    this.extention = extention;
    this.type = type;
  }

  public static String getTypeByExtention(String extention) {
    for (ContentType contentType : values()) {
      if (contentType.extention.equals(extention)) {
        return contentType.type;
      }
    }
    throw new UnsupportedContentType("지원하지 않는 Content Type 입니다." + extention);
  }
}
