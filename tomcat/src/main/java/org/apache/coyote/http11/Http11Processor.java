package org.apache.coyote.http11;

import camp.nextstep.exception.InvalidRequestException;
import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.HttpHeaders;
import camp.nextstep.http.HttpRequest;
import camp.nextstep.http.HttpResponse;
import camp.nextstep.http.RequestHandler;
import camp.nextstep.http.RequestLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http11Processor implements Runnable, Processor {

  private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
  private final RequestHandler requestHandler;
  private final Socket connection;

  public Http11Processor(final Socket connection) {
    this.connection = connection;
    this.requestHandler = new RequestHandler();

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

      HttpRequest httpRequest = parseHttpRequest(bufferedInputStream);
      HttpResponse httpResponse = requestHandler.handleRequest(httpRequest);

      outputStream.write(httpResponse.buildResponse().getBytes());
      outputStream.flush();
    } catch (IOException | UncheckedServletException e) {
      log.error(e.getMessage(), e);
    }
  }

  private HttpRequest parseHttpRequest(final BufferedReader requestStream) throws IOException {
    RequestLine requestLine = parseRequestLine(requestStream);
    HttpHeaders headers = parseHeaders(requestStream);
    String body = parseBody(requestStream, headers);

    return new HttpRequest(requestLine, headers, body);
  }

  private RequestLine parseRequestLine(BufferedReader requestStream) throws IOException {
    String line = requestStream.readLine();
    return RequestLine.parse(line);
  }

  private HttpHeaders parseHeaders(BufferedReader requestStream) throws IOException {
    List<String> headerLines = new ArrayList<>();
    String line;
    while ((line = requestStream.readLine()) != null && !line.isEmpty()) {
      headerLines.add(line);
    }
    return HttpHeaders.from(headerLines);
  }

  private String parseBody(BufferedReader requestStream, HttpHeaders headers) throws IOException {
    int contentLength = headers.getContentLength();
    if (contentLength > 0) {
      char[] body = new char[contentLength];
      requestStream.read(body, 0, contentLength);
      return new String(body);
    }
    return "";
  }

}
