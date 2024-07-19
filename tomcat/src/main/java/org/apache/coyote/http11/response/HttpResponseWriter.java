package org.apache.coyote.http11.response;

import java.util.Map;

public class HttpResponseWriter {
	private final String response;

	public HttpResponseWriter(final String response) {
		this.response = response;
	}

	public static HttpResponseWriter parsingResponse(HttpResponse httpResponse) {
		StatusLine statusLine = httpResponse.getStatusLine();
		String responseBody = httpResponse.getResponseBody();
		Map<String, String> headers = httpResponse.getHeaders();

		StringBuffer header = new StringBuffer();
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			header.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
		}

		final var response = String.join("\r\n",
				statusLine.getHttpVersion() + " " + statusLine.getStatusCode() + " " + statusLine.getStatusReason(),
				header.toString(),
				"",
				responseBody);

		return new HttpResponseWriter(response);
	}

	public String getResponse() {
		return response;
	}
}
