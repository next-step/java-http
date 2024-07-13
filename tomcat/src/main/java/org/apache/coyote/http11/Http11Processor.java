package org.apache.coyote.http11;

import camp.nextstep.exception.InvalidRequestException;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.ContentType;
import camp.nextstep.http.RequestLine;
import camp.nextstep.util.FileUtils;
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
  public static final String PATH_SEPARATOR = "/";
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
    try (final var inputStream = connection.getInputStream(); final var outputStream = connection.getOutputStream(); final var bufferedInputStream = new BufferedReader(
        new InputStreamReader(inputStream))) {

      if (bufferedInputStream.lines() == null) {
        throw new InvalidRequestException("request가 null이거나 비어있습니다.");
      }
      var responseBody = "Hello world!";

      RequestLine requestLine = RequestLine.parse(bufferedInputStream.readLine());
      String path =
          requestLine.getPath().equals(PATH_SEPARATOR) ? "index.html" : requestLine.getPath();
      String extension = FileUtils.getFileExtension(path);

      URL url = getClass().getClassLoader().getResource(staticPath + path);

      if (url != null) {
        responseBody = new String(Files.readAllBytes(new File(url.getFile()).toPath()));
      }

      final var response = String.join("\r\n", "HTTP/1.1 200 OK ",
          "Content-Type: " + ContentType.getTypeByExtention(extension) + ";charset=utf-8 ",
          "Content-Length: " + responseBody.getBytes().length + " ", "", responseBody);

      outputStream.write(response.getBytes());
      outputStream.flush();
    } catch (IOException | UncheckedServletException e) {
      log.error(e.getMessage(), e);
    }
  }
}
