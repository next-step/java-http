package org.apache.coyote.http11.request;

import java.util.HashMap;
import java.util.Map;

public class RequestLineParser {
    public static final String HTTP_METHOD_AND_PATH_DELIMITER_CHARACTER = " ";
    public static final String PROTOCOL_AND_VERSION_DELIMITER_CHARACTER = "/";
    public static final String PATH_AND_QUERY_STRING_DELIMITER_CHARACTER = "\\?";
    public static final String QUERY_STRING_DELIMITER_CHARACTER = "&";
    public static final String KEY_AND_VALUE_DELIMITER_CHARACTER = "=";

    public static RequestLine createRequestLine(String request) {
        HttpMethod httpMethod = getHttpMethod(request);
        Path path = getPath(request);
        String protocol = getProtocol(request);
        String version = getVersion(request);
        return new RequestLine(httpMethod, path, protocol, version);
    }

    private static HttpMethod getHttpMethod(String request) {
        String[] tokens = splitWord(request, HTTP_METHOD_AND_PATH_DELIMITER_CHARACTER);
        return HttpMethod.valueOf(tokens[0]);
    }

    private static String getProtocol(String request) {
        String[] tokens = splitWord(request, HTTP_METHOD_AND_PATH_DELIMITER_CHARACTER);
        String[] protocolAndVersion = splitWord(tokens[2], PROTOCOL_AND_VERSION_DELIMITER_CHARACTER);
        return protocolAndVersion[0];
    }

    private static String getVersion(String request) {
        String[] tokens = splitWord(request, HTTP_METHOD_AND_PATH_DELIMITER_CHARACTER);
        String[] protocolAndVersion = splitWord(tokens[2], PROTOCOL_AND_VERSION_DELIMITER_CHARACTER);
        return protocolAndVersion[1];
    }

    private static String[] splitWord(String token, String delimiter) {
        return token.split(delimiter);
    }

    private static Path getPath(String request) {
        String[] tokens = splitWord(request, HTTP_METHOD_AND_PATH_DELIMITER_CHARACTER);
        String path = tokens[1];
        if (hasQuestionMark(tokens)) {
            String[] pathAndQueryString = path.split(PATH_AND_QUERY_STRING_DELIMITER_CHARACTER);
            String urlPath = pathAndQueryString[0];
            QueryStrings queryStrings = parseQueryStrings(pathAndQueryString[1].split(QUERY_STRING_DELIMITER_CHARACTER));
            return new Path(urlPath, queryStrings);
        }
        return new Path(path, QueryStrings.emptyQueryStrings());
    }

    private static QueryStrings parseQueryStrings(String[] queryStringList){
        Map<String, String> queryStringMap = new HashMap<>();
        for (String query : queryStringList) {
            String[] keyAndValue = query.split(KEY_AND_VALUE_DELIMITER_CHARACTER);
            queryStringMap.put(keyAndValue[0], keyAndValue[1]);
        }
        return new QueryStrings(queryStringMap);
    }

    private static boolean hasQuestionMark(String[] tokens) {
        return tokens[1].contains("?");
    }
}
