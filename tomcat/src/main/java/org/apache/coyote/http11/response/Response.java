package org.apache.coyote.http11.response;

import org.apache.coyote.http11.request.model.ContentType;
import org.apache.coyote.http11.request.model.Cookies;

public class Response {
	private final String response;

	public Response(final String response) {
		this.response = response;
	}

	private static Response from(final String response) {
		return new Response(response);
	}

	public static Response createResponse(ResponseResource responseResource) {
		String extension = responseResource.parseExtension();
		String contentType = ContentType.findByExtension(extension).getContentType();
		String responseBody = responseResource.getResponseBody();
		StatusCode statusCode = responseResource.getStatusCode();
		Cookies cookies = responseResource.getCookies();

		final var response = String.join("\r\n",
				"HTTP/1.1 " + statusCode.getCode() + " " + statusCode.name(),
				"Content-Type: " + contentType + ";charset=utf-8 ",
				"Content-Length: " + responseBody.getBytes().length + " ",
				cookies.getResponseCookies(),
				"",
				responseBody);

		return from(response);
	}

	public byte[] getBytes() {
		return response.getBytes();
	}
}
