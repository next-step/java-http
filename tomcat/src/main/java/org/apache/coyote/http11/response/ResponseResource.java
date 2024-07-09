package org.apache.coyote.http11.response;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import org.apache.coyote.http11.request.Path;
import org.apache.coyote.http11.request.QueryStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.NoSuchElementException;

public class ResponseResource {

	private static final Logger log = LoggerFactory.getLogger(ResponseResource.class);

	private final URL resource;
	private final String fileName;

	private ResponseResource(final URL resource, String fileName) {
		this.resource = resource;
		this.fileName = fileName;
	}

	public static ResponseResource of(final Path path) {
		if(path.urlPath().equals("/")) {
			return new ResponseResource(ResponseResource.class.getClassLoader().getResource("static/index.html"),"index.html");
		}

		if (path.urlPath().equals("/login")) {
			QueryStrings queryStrings = path.queryStrings();
			String account = queryStrings.getQueryStringValueByKey("account");
			String password = queryStrings.getQueryStringValueByKey("password");
			login(account, password);
			URL resultResource = ResponseResource.class.getClassLoader().getResource("static/login.html");
			return new ResponseResource(resultResource, "login.html");
		}
		URL resultResource = ResponseResource.class.getClassLoader().getResource("static" + path.urlPath());
		return new ResponseResource(resultResource, path.urlPath());
	}

	public URL getResource() {
		return resource;
	}

	public static void login(String account, String password) {
		final User user = InMemoryUserRepository.findByAccount(account).orElseThrow(NoSuchElementException::new);
		if (user.checkPassword(password)) {
			log.info("user {}", user);
		}
	}

	public String parseExtension() {
		return fileName.substring(fileName.lastIndexOf("."));
	}
}
