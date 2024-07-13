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

### 요구사항 3. Cookie 에 JSESSIONID 값 저장하기

- HttpHeaders 에서 Cookie 를 추출한다.
- Cookie 클래스를 추가하고 값들을 저장한다.
- HTTP Request Header의 Cookie에 `JSESSIONID` 가 없으면 HTTP Response Header에 `JSESSIONID` 를 추가한다.
- `JSESSIONID`는 UUID 를 이용한다.

### 요구사항 4. Session 구현하기

- 쿠키에서 전달 받은 `JSESSIONID` 의 값으로 로그인 여부를 체크할 수 있어야 한다.
- 로그인 성공 시 Session 에 User 객체를 저장한다.
- 로그인이 된 상태에서 `/login` 페이지에 HTTP GET 요청으로 접근하면 index.html 페이지로 리다이렉트 한다.

## 4단계 - 리팩터링

### 요구사항 1 - HttpRequest 클래스 구현하기

- HttpRequest 처리를 담당하는 클래스
- RequestLine
    - Method, Path, Version/Protocol 로 구성
- Headers
- Body
    - Headers 의 Content-Length 길이만큼만 읽는다.
    - Headers 의 Content-Length 가 없으면 읽지 않는다.

### 요구사항 2 - HttpResponse 클래스 구현하기

- HttpResponse 처리를 담당하는 클래스
- StatusLine
    - Version/Protocol, StatusCode, ReasonPhrase 로 구성
- Headers
- Body
-

### 요구사항 3 - Controller 인터페이스 추가하기

- Controller 인터페이스를 추가한다.
    ```java
    public interface Controller {
        void service(HttpRequest request, HttpResponse response) throws Exception;
    }
    ```
- AbstractController 추상 클래스를 추가한다.
    ```java
    public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        // http method 분기문
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws Exception { /* NOOP */ }
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception { /* NOOP */ }
    }
    ```
- 각 분기별로 AbstractController 를 구현하게끔 한다.
- 각 컨트롤러 구현체들을 RequestMapping 에 저장한다
    ```java
    public class RequestMapping {
        ...
    public Controller getController(HttpRequest request) {
        ...
        }
    }
    ```

## 5단계 - 동시성 확장하기

### 요구사항 1 - Executors 로 Thread Pool 적용

- `Executors` 클래스를 사용해 `ExecutorsService` 객체를 만든다.
  > Executors 로 생성할 수 있는 스레드 풀
  >
  > 1. 고정된 스레드 풀 (FixedThreadPool): 고정된 수의 스레드를 사용하여 스레드 수를 제한하고 싶을 때 사용
  > 2. 캐시된 스레드 풀 (CachedThreadPool): 짧은 시간에 많은 작업을 처리하고, 유휴 스레드를 재사용하고 싶을 때 사용
  > 3. 스케줄된 스레드 풀 (ScheduledThreadPool): 지정된 시간 후에 작업을 실행하거나 주기적으로 작업을 실행하고 싶을 때 사용
  > 4. 단일 스레드 풀 (SingleThreadExecutor): 단일 스레드로 순차적으로 작업을 처리하고 싶을 때 사용
  >
  > ThreadPoolExecutor 클래스로 원하는 형태의 스레드 풀 구성이 가능하다
    - 최대 thread 수(maxThreads) 는 250
    - 대기 요청 큐(acceptCount) 는 100
    - 최소 유지 thread 수(coreThreads) 는 10
    - 유휴 thread 유지 시간(keepAliveTime) 는 60
    - 대기 요청 큐가 다 쌓였을 시 정책은 AbortPolicy, CallerRunsPolicy, DiscardPolicy 등등이 있다
    
### 요구사항 2 - 동시성 컬렉션 사용하기

- `SessionManager` 는 전역에서 접근 가능한 static 변수이다.
- 여러 Thread 에서 접근하여 읽고 쓰면 동시성 문제가 발생한다.
- `ConcurrentHashMap` 을 적용하면 어느정도 해소 된다.
