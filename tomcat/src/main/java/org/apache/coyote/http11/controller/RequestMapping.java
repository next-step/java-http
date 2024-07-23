package org.apache.coyote.http11.controller;

import java.util.Map;

public class RequestMapping {

	private final Map<String, Controller> controllers;
	private final Controller staticController = new StaticController();

	public RequestMapping() {
		controllers = Map.of(
				"/", new RootController(),
				"/login", new LoginController(),
				"/register", new RegisterController()
		);
	}

	public Controller getController(final String path) {
		if(path.startsWith("/css")||path.startsWith("/js")||path.startsWith("/images")||path.startsWith("/fonts")||path.startsWith("/favicon.ico")) {
			return staticController;
		}

		Controller controller = controllers.get(path);
		return controller;
	}
}
