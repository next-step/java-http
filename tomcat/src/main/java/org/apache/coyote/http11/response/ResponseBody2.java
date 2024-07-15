package org.apache.coyote.http11.response;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public class ResponseBody2 {
	private final String responseBody;

	public ResponseBody2(final String filePath) throws IOException {
		this.responseBody = createResponseBody(filePath);
	}

	public String getResponseBody() {
		return responseBody;
	}

	private String createResponseBody(String filePath) throws IOException {
		URL resource = createResource(filePath);
		String responseBody = new String(Files.readAllBytes(new File(resource.getFile()).toPath()));
		return responseBody;
	}

	private  URL createResource(String filePath) {
		return getClass().getClassLoader().getResource("static" + filePath);
	}
}
