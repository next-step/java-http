package org.apache.coyote.http11;


import camp.nextstep.exception.UncheckedServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public final class RequestParser {

    public static RequestLine parse(InputStream inputStream, Charset charset)  {

        try {
            final var br = new BufferedReader(new InputStreamReader(inputStream, charset));
            final var readLine = br.readLine();
            return new RequestLine(readLine);
        } catch (IOException e) {
            throw new UncheckedServletException(e);
        }
    }
}
