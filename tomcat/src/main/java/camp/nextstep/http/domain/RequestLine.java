package camp.nextstep.http.domain;

import camp.nextstep.enums.Protocol;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.http.enums.HttpVersion;
import camp.nextstep.http.exception.InvalidHttpRequestSpecException;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

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
    private static final int PATH_INDEX = 0;
    private static final int QUERY_PARAM_INDEX = 1;

    private HttpMethod method;
    private String path;
    private Protocol protocol;
    private HttpVersion version;
    private Map<String, String> queryParams;

    private RequestLine(
            HttpMethod method,
            String path,
            Protocol protocol,
            HttpVersion version
    ) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    private RequestLine(
            HttpMethod method,
            String path,
            Protocol protocol,
            HttpVersion version,
            Map<String, String> queryParams
    ) {
        this.method = method;
        this.path = path;
        this.protocol = protocol;
        this.version = version;
        this.queryParams = queryParams;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
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

        String[] pathStrs = requestArgs[HTTP_PATH_INDEX].split("\\?");
        if (pathStrs.length == 2) {
            Map<String, String> queryParams = getQueryParams(pathStrs[QUERY_PARAM_INDEX]);

            return new RequestLine(
                    method,
                    pathStrs[PATH_INDEX],
                    protocol,
                    version,
                    queryParams
            );
        } else {
            return new RequestLine(
                    method,
                    pathStrs[PATH_INDEX],
                    protocol,
                    version
            );
        }
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
        if (version == null) {
            throw new InvalidHttpRequestSpecException("지원하는 버전이 아닙니다");
        }
        return version;
    }

    private static Map<String, String> getQueryParams(String queryParamStr) {
        String[] queryParamArgs = queryParamStr.split("&");
        return Arrays.stream(queryParamArgs)
                .map(v -> v.split("="))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));
    }
}
