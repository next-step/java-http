package org.apache.coyote;

import org.apache.coyote.http11.HttpRequest;

public interface Parser {
    HttpRequest parse(String requestLine);
}
