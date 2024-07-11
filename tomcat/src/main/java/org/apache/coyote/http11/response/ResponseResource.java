package org.apache.coyote.http11.response;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.request.Path;
import org.apache.coyote.http11.request.QueryStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ResponseResource {

	private static final Logger log = LoggerFactory.getLogger(ResponseResource.class);

	private final String responseBody;
	private final String filePath;
	private final StatusCode statusCode;

	private ResponseResource(final String responseBody, String filePath, StatusCode statusCode) {
		this.responseBody = responseBody;
		this.filePath = filePath;
		this.statusCode = statusCode;
	}

	public static ResponseResource of(final Path path) throws IOException {
		if(isRootPath(path)) {
			String filePath = "/index.html";
			String responseBody = new ResponseBody(filePath).getResponseBody();
			return new ResponseResource(responseBody, filePath, StatusCode.OK);
		}

		UrlPath.findUrlPath(path.urlPath());
		if (path.urlPath().equals("/login")) {
			QueryStrings queryStrings = path.queryStrings();
			String account = queryStrings.getQueryStringValueByKey("account");
			String password = queryStrings.getQueryStringValueByKey("password");
			boolean loginSuccess = login(account, password);
			if (loginSuccess) {
				String filePath = "/index.html";
				String responseBody = new ResponseBody(filePath).getResponseBody();
				return new ResponseResource(responseBody, filePath, StatusCode.FOUND);
			}
			String filePath = "/401.html";
			String responseBody = new ResponseBody(filePath).getResponseBody();
			return new ResponseResource(responseBody, filePath, StatusCode.NOT_FOUND);

		}

		String responseBody = new ResponseBody(path.urlPath()).getResponseBody();
		return new ResponseResource(responseBody, path.urlPath(), StatusCode.OK);
	}

	public String getResponseBody() {
		return responseBody;
	}

	public String getFilePath() {
		return filePath;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

	public String parseExtension() {
		if(hasNotExtension()) {
			throw new NoSuchElementException("확장자가 없습니다.");
		}
		return filePath.substring(filePath.lastIndexOf("."));
	}

	private static boolean login(String account, String password) {
		final User user = InMemoryUserRepository.findByAccount(account).orElseThrow(NoSuchElementException::new);
		if (user.checkPassword(password)) {
			log.info("user {}", user);
			return true;
		}
		return false;
	}

	private static boolean isRootPath(final Path path) {
		return path.urlPath().equals("/");
	}

	private boolean hasNotExtension() {
		return filePath.lastIndexOf(".") < 0;
	}
}
