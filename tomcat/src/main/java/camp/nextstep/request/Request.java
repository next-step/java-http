package camp.nextstep.request;

import static camp.nextstep.request.Cookie.JSESSIONID_NAME;

public class Request {
    private final RequestLine requestLine;
    private final RequestHeaders requestHeaders;
    private final RequestCookies requestCookies;
    private final QueryParameters requestBody;

    public Request(RequestLine requestLine,
                   RequestHeaders requestHeaders,
                   RequestCookies requestCookies,
                   QueryParameters requestBody) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.requestCookies = requestCookies;
        this.requestBody = requestBody;
    }

    public boolean isGET() {
        return requestLine.getMethod() == RequestMethod.GET;
    }

    public boolean isPOST() {
        return requestLine.getMethod() == RequestMethod.POST;
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public RequestHeaders getRequestHeaders() {
        return requestHeaders;
    }

    public QueryParameters getRequestBody() {
        return requestBody;
    }

    public boolean hasSessionCookie() {
        return requestCookies.hasKey(JSESSIONID_NAME);
    }
}
