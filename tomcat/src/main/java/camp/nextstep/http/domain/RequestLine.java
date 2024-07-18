package camp.nextstep.http.domain;

import camp.nextstep.enums.Protocol;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.http.enums.HttpVersion;
import camp.nextstep.http.exception.InvalidHttpRequestSpecException;

import static camp.nextstep.http.enums.HttpVersion.NONE;
import static camp.nextstep.http.enums.HttpVersion.getHttpVersionByVersion;
import static camp.nextstep.util.EnumUtil.searchEnum;

public class RequestLine {
    private static final int REQUEST_LINE_ARGS_COUNT = 3;
    private static final int HTTP_PROTOCOL_ARGS_COUNT = 2;
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int HTTP_PATH_INDEX = 1;
    private static final int HTTP_PROTOCOL_ARG_INDEX = 2;
    private static final int PROTOCOl_INDEX = 0;
    private static final int HTTP_VERSION_INDEX = 1;

    private HttpMethod method;
    private Protocol protocol;
    private HttpVersion version;
    private Path path;

    private RequestLine(
            HttpMethod method,
            Path path,
            Protocol protocol,
            HttpVersion version
    ) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public Path getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public static RequestLine createRequestLineByRequestLineStr(String requestLineStr) {
        String[] requestArgs = requestLineStr.split("\\s+");
        if (requestArgs.length != REQUEST_LINE_ARGS_COUNT) {
            throw new InvalidHttpRequestSpecException("http 스펙의 파라미터 개수가 유효하지 않습니다");
        }

        HttpMethod method = getHttpMethodFromStr(requestArgs[HTTP_METHOD_INDEX]);

        String[] httpArgs = requestArgs[HTTP_PROTOCOL_ARG_INDEX].split("/");
        if (httpArgs.length != HTTP_PROTOCOL_ARGS_COUNT) {
            throw new InvalidHttpRequestSpecException("유효한 프로토콜 형식이 아닙니다");
        }

        Protocol protocol = getProtocolFromStr(httpArgs[PROTOCOl_INDEX]);
        HttpVersion version = getHttpVersionFromStr(httpArgs[HTTP_VERSION_INDEX]);
        Path path = Path.createPathByPathStr(requestArgs[HTTP_PATH_INDEX]);

        return new RequestLine(
                method,
                path,
                protocol,
                version
        );
    }

    private static HttpMethod getHttpMethodFromStr(String httpMethodStr) {
        HttpMethod method = searchEnum(HttpMethod.class, httpMethodStr);
        if (method == null) {
            throw new InvalidHttpRequestSpecException("지원하는 메소드가 아닙니다");
        }
        return method;
    }

    private static Protocol getProtocolFromStr(String protocolStr) {
        Protocol protocol = searchEnum(Protocol.class, protocolStr);
        if (protocol == null) {
            throw new InvalidHttpRequestSpecException("지원하는 프로토콜이 아닙니다.");
        }

        if (protocol != Protocol.HTTP) {
            throw new InvalidHttpRequestSpecException("http 프로토콜이 아닙니다");
        }
        return protocol;
    }

    private static HttpVersion getHttpVersionFromStr(String httpVersionStr) {
        HttpVersion version = getHttpVersionByVersion(httpVersionStr);
        if (version == NONE) {
            throw new InvalidHttpRequestSpecException("지원하는 버전이 아닙니다");
        }
        return version;
    }
}
