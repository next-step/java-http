# 만들면서 배우는 스프링

[Next Step - 과정 소개](https://edu.nextstep.camp/c/4YUvqn9V)

## 톰캣 구현하기

### 학습목표

- 웹 서버 구현을 통해 HTTP 이해도를 높인다.
- HTTP의 이해도를 높혀 성능 개선할 부분을 찾고 적용할 역량을 쌓는다.
- 서블릿에 대한 이해도를 높인다.
- 스레드, 스레드풀을 적용해보고 동시성 처리를 경험한다.

### 시작 가이드

1. 미션을 시작하기 전에 파일, 입출력 스트림 학습 테스트를 먼저 진행합니다.
    - [File, I/O Stream](study/src/test/java/study)
    - 나머지 학습 테스트는 다음 강의 시간에 풀어봅시다.
2. 학습 테스트를 완료하면 LMS의 1단계 미션부터 진행합니다.

## 학습 테스트

1. [File, I/O Stream](study/src/test/java/study)
2. [HTTP Cache](study/src/test/java/cache)
3. [Thread](study/src/test/java/thread)

## 1단계 - TDD 실습

```http request
GET /docs/index.html HTTP/1.1
Host: www.nowhere123.com
Accept: image/gif, image/jpeg, */*
Accept-Language: en-us
Accept-Encoding: gzip, deflate
User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)
(blank line)
```

```http response
HTTP/1.1 200 OK
Date: Sun, 18 Oct 2009 08:56:53 GMT
Server: Apache/2.2.14 (Win32)
Last-Modified: Sat, 20 Nov 2004 07:16:26 GMT
ETag: "10000000565a5-2c-3e94b66c2e680"
Accept-Ranges: bytes
Content-Length: 44
Connection: close
Content-Type: text/html
X-Pad: avoid browser bug
  
<html><body><h1>It works!</h1></body></html>
```

### 요구사항 1 - GET 요청

- RequestLine 파싱 ("GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.)
    - method는 GET
    - path는 /users
    - protocol은 HTTP
    - version은 1.1

- RequestLine 을 공백으로 split 한다.
    - 분리 시 Method, Path, Protocol/Version 를 추출한다.
    - 위 세가지가 포함되어 있지 않으면 예외가 발생한다.

### 요구사항 2 - POST 요청

- RequestLine 파싱 ("POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.)
    - method는 POST
    - path는 /users
    - protocol은 HTTP
    - version은 1.1

### 요구사항 3 - Query String 파싱

- HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
- "GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1" 파싱해 Query String 을 추출할 수 있다.

## 2단계 - HTTP 서버 구현하기

### 요구사항 1 - GET /index.html 응답하기

- http://localhost:8080/index.html 에 접근할 수 있도록 한다
    ```http request
      GET /index.html HTTP/1.1
      Host: localhost:8080
      Connection: keep-alive
      Accept: */*
    ```
    - `Http11ProcessorTest` 를 통과 시켜야 한다.
        - `/`요청은 `Hello world!` 를 OutputStream 에 쓴다.
        - `/index.html`요청은 해당 파일을 찾아 OutputStream 에 쓴다.
            - 해당 파일이 없는경우 `404.html` 을 OutputStream 에 쓴다.

### 요구사항 2 - CSS 지원하기

- 사용자가 페이지를 열었을 때 CSS 파일도 호출하도록 기능을 추가하자.
    ```http request
        GET /css/styles.css HTTP/1.1
        Host: localhost:8080
        Accept: text/css,*/*;q=0.1
        Connection: keep-alive
    ```
    - 요청 path 의 확장자를 확인해 response header 의 Content-Type 을 바꿔준다.
        - html -> text/html
        - css -> text/css
        - js -> text/javascript (참고 [rfc9239](https://www.rfc-editor.org/rfc/rfc9239))

### 요구사항 3 - Query String 파싱

- http://localhost:8080/login?account=gugu&password=password 으로 접속하면 login.html 를 보여준다.
    - Query String 에서 account 와 password 를 추출한다.
    - 해당 account 로 User 를 찾는다
        - 존재하지 않으면 예외를 던진다.
        - 존재하면 password 를 비교한다.
            - password 가 틀리면 예외를 던진다.
            - password 가 동일하면 콘솔에 User 객체를 출력한다
    - `login.html` 을 OutputStream 에 쓴다.

## 3단계 - 로그인 구현하기

### 요구사항 1 - HTTP Status Code 302

- 로그인 여부에 따라 페이지를 이동한다
    - 응답 헤더에 `HttpStatusCode 302` 를 반환한다.
    - account 와 password 가 일치하면 `/index.html` 로 리다이렉트 한다.
    - 존재하지 않는 account 이거나 password 가 일치가 일치하지 않으면 `401.html`로 리다이렉트 한다.

### 요구사항 2 - POST 방식으로 회원가입

- http://localhost:8080/register 에 접근하면 register.html 를 OutputStream 에 쓴다
    - 페이지 접근은 GET 을 사용한다.
    - 회원가입 버튼을 누르면 POST 요청을 사용한다.
    - 회원가입을 완료하면 `index.html` 로 리다이렉트 한다.
- 로그인 페이지도 버튼을 눌렀을때 POST 방식으로 전송하도록 변경한다.
