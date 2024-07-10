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

## 미션

### 1단계 - TDD 실습
- [x] 요구사항 1 - GET 요청
  - HTTP GET 요청에 대한 RequestLine을 파싱한다.
  - 파싱하는 로직 구현을 TDD로 구현한다.
  - 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method는 GET
    - path는 /users
    - protocol은 HTTP
    - version은 1.1
- [x] 요구사항 2 - POST 요청
  - HTTP POST 요청에 대한 RequestLine을 파싱한다.
  - 파싱하는 로직 구현을 TDD로 구현한다.
  - 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
    - method는 POST
    - path는 /users
    - protocol은 HTTP
    - version은 1.1
- [x] 요구사항 3 - Query String 파싱
  - HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
  - 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
  - 파싱하는 로직 구현을 TDD로 구현한다.
  - Query String 예 - GET 요청
    - GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1
- [x] 요구사항 4 - enum 적용(선택)
  - HTTP method인 GET, POST를 enum으로 구현한다.
### 2단계 - HTTP 서버 구현하기
- [x] 요구사항 1 - GET /index.html 응답하기
  - 인덱스 페이지(http://localhost:8080/index.html)에 접근할 수 있도록 만들자.
  - Http11ProcessorTest 테스트 클래스의 모든 테스트를 통과하면 된다.
  - 브라우저에서 요청한 HTTP Request Header는 다음과 같다.
  ``` text
  GET /index.html HTTP/1.1
  Host: localhost:8080
  Connection: keep-alive
  Accept: */* 
  ```

- [x] 요구사항 2 - CSS 지원하기
  - 인덱스 페이지에 접속하니까 화면이 이상하게 보인다.
  - 개발자 도구를 열어서 에러 메시지를 체크해보니 브라우저가 CSS를 못 찾고 있다.
  - 사용자가 페이지를 열었을 때 CSS 파일도 호출하도록 기능을 추가하자.
  ```text
  GET /css/styles.css HTTP/1.1
  Host: localhost:8080
  Accept: text/css,*/*;q=0.1
  Connection: keep-alive
  ```
- [x] 요구사항 3 - Query String 파싱
  - http://localhost:8080/login?account=gugu&password=password으로 접속하면 로그인 페이지(login.html)를 보여주도록 만들자.
  - 그리고 로그인 페이지에 접속했을 때 Query String을 파싱해서 아이디, 비밀번호가 일치하면 콘솔창에 로그로 회원을 조회한 결과가 나오도록 만들자.
### 3단계 - HTTP 서버 구현하기
- [ ] 요구사항 1 - HTTP Status Code 302를 이용해 Redirection 구현
  - /login 페이지에서 아이디는 gugu, 비밀번호는 password를 입력하자. 
  - 로그인에 성공하면 응답 헤더에 http status code를 302로 반환하고 /index.html로 리다이렉트 한다. 
  - 로그인에 실패하면 401.html로 리다이렉트한다.
- [ ] 요구사항 2 - POST 방식으로 회원가입
  - http://localhost:8080/register으로 접속하면 회원가입 페이지(register.html)를 보여준다. 
  - 회원가입 페이지를 보여줄 때는 GET을 사용한다. 
  - 회원가입을 버튼을 누르면 HTTP method POST를 이용하여 회원가입. 
  - 회원가입을 완료하면 index.html로 리다이렉트한다. 
  - 로그인 페이지도 버튼을 눌렀을 때 GET 방식에서 POST 방식으로 전송하도록 변경하자.
- [ ] 요구사항 3 - Cookie에 JSESSIONID 값 저장하기
  - 로그인에 성공하면 쿠키와 세션을 활용해서 로그인 상태를 유지해야 한다.
  - HTTP 서버는 세션을 사용해서 서버에 로그인 여부를 저장한다.
  - 세션을 구현하기 전에 먼저 쿠키를 구현해본다. 
  - 자바 진영에서 세션 아이디를 전달하는 이름으로 JSESSIONID를 사용한다. 
  - 서버에서 HTTP 응답을 전달할 때 응답 헤더에 Set-Cookie를 추가하고 JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46 형태로 값을 전달하면 클라이언트 요청 헤더의 Cookie 필드에 값이 추가된다.
  - 서버로부터 쿠키 설정된 클라이언트의 HTTP Request Header 예시
  ``` text
  GET /index.html HTTP/1.1
  Host: localhost:8080
  Connection: keep-alive
  Accept: */*
  Cookie: yummy_cookie=choco; tasty_cookie=strawberry; JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46
  ```
  - Cookie 클래스를 추가하고 HTTP Request Header의 Cookie에 JSESSIONID가 없으면 HTTP Response Header에 Set-Cookie를 반환해주는 기능을 구현한다.
  ``` text 
  HTTP/1.1 200 OK
  Set-Cookie: JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46
  Content-Length: 5571
  Content-Type: text/html;charset=utf-8;
  ```


