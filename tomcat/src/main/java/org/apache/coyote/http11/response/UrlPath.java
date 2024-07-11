package org.apache.coyote.http11.response;

import java.util.Arrays;

public enum UrlPath {
	LOGIN("/login"),
	DEFAULT("/index.html"),
	;

	private final String urlPath;

	UrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public static UrlPath findUrlPath(final String urlPath) {
		return Arrays.stream(values())
				.filter(it -> urlPath.equals(it.urlPath))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("do not find UrlPath"));
	}
}
