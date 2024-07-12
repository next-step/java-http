package org.apache.coyote.http11.response;

import org.apache.coyote.http11.request.model.ContentType;

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

		final var response = String.join("\r\n",
				"HTTP/1.1 " + statusCode.getCode() + " " + statusCode.name(),
				"Content-Type: " + contentType + ";charset=utf-8 ",
				"Content-Length: " + responseBody.getBytes().length + " ",
				"",
				responseBody);

		return from(response);
	}

	public byte[] getBytes() {
		return response.getBytes();
	}
}
