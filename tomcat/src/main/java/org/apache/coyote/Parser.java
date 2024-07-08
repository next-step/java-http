package org.apache.coyote;

import org.apache.coyote.http11.HttpServletRequest;

public interface Parser {
    HttpServletRequest parse(String requestLine);
}
