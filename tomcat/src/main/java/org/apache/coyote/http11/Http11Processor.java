package org.apache.coyote.http11;

import camp.nextstep.exception.InvalidRequestException;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.RequestLine;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http11Processor implements Runnable, Processor {

  private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
  public static final String staticPath = "static/";
  private final Socket connection;

  public Http11Processor(final Socket connection) {
    this.connection = connection;
  }

  @Override
  public void run() {
    log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
    process(connection);
  }

  @Override
  public void process(final Socket connection) {
    try (final var inputStream = connection.getInputStream();
        final var outputStream = connection.getOutputStream();
        final var bufferedInputStream = new BufferedReader(new InputStreamReader(inputStream))) {

      if (bufferedInputStream.lines() == null) {
        throw new InvalidRequestException("request가 null이거나 비어있습니다.");
      }
      var responseBody = "Hello world!";

      RequestLine requestLine = RequestLine.parse(bufferedInputStream.readLine());

      if (!requestLine.getPath().equals("/")) {
          
        URL url = getClass().getClassLoader().getResource(staticPath + requestLine.getPath());
        responseBody = new String(Files.readAllBytes(new File(url.getFile()).toPath()));
      }

      final var response = String.join("\r\n",
          "HTTP/1.1 200 OK ",
          "Content-Type: text/html;charset=utf-8 ",
          "Content-Length: " + responseBody.getBytes().length + " ",
          "",
          responseBody);

      outputStream.write(response.getBytes());
      outputStream.flush();
    } catch (IOException | UncheckedServletException e) {
      log.error(e.getMessage(), e);
    }
  }
}
