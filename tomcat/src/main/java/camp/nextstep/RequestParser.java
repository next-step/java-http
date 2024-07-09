package camp.nextstep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import camp.nextstep.model.dto.RequestLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestParser {

	private static final Logger log = LoggerFactory.getLogger(RequestParser.class);

	public static RequestLine parseRequest(InputStream is) {
		List<String> request = getRequest(is);
		String[] requestInfos = request.get(0).split(" "); // GET /hello HTTP/1.1
		String[] protocolAndVersion = requestInfos[2].split("\\/");
		if (!requestInfos[1].contains("\\?")) {
			return RequestLine.of(requestInfos[0], requestInfos[1], protocolAndVersion[0], protocolAndVersion[1]);
		}
		String[] uriAndQueryString = requestInfos[1].split("\\?");
		Map<String, String> queryStringMap = getQueryStringMap(uriAndQueryString[1]);
		return RequestLine.of(requestInfos[0], uriAndQueryString[0], protocolAndVersion[0], protocolAndVersion[1], queryStringMap);
	}

	private static Map<String, String> getQueryStringMap(String queryStrings) {
		return Arrays.stream(queryStrings.split("\\&"))
				.map(queryString -> queryString.split("\\="))
				.collect(Collectors.toMap(
						keyAndValues -> keyAndValues[0],
						keyAndValues -> keyAndValues.length > 1 ? keyAndValues[1] : "")
				);
	}

	private static List<String> getRequest(InputStream is) {
		List<String> request = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = br.readLine();
			while (line != null) {
				request.add(line);
				line = br.readLine();
			}
			return request;
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException("유효하지 않은 요청입니다.");
		}
	}
}
