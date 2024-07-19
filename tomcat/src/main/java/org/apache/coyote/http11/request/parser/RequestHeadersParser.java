package org.apache.coyote.http11.request.parser;

import org.apache.coyote.http11.request.model.RequestHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeadersParser {
    public static RequestHeaders parse(List<String> requestHeaders) {
        Map<String, Object> headersMap = new HashMap<>();
        for (int i = 1; i < requestHeaders.size(); i++) {
            //todo: 헤더에서 쿠키값 가져올때 content-lenght도 가져오는 문제
            String[] header = requestHeaders.get(i).split(": ", 2);
            headersMap.put(header[0], header[1]);
        }
        return new RequestHeaders(headersMap);
    }
}
