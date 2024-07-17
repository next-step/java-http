package org.apache.coyote.http11.controller;

import java.util.Map;

public class RequestMapping {

	private final Map<String, Controller> controllers;

	public RequestMapping() {
		controllers = Map.of(
				"/", new RootController(),
				"/login", new LoginController(),
				"/register", new RegisterController()
		);
	}

	public Controller getController(final String path) {
		return controllers.get(path);
	}
}
