# 만들면서 배우는 스프링
[Next Step - 과정 소개](https://edu.nextstep.camp/c/4YUvqn9V)

## 톰캣 구현하기

### 학습목표
- 웹 서버 구현을 통해 HTTP 이해도를 높인다.
- HTTP의 이해도를 높혀 성능 개선할 부분을 찾고 적용할 역량을 쌓는다.
- 서블릿에 대한 이해도를 높인다.
- 스레드, 스레드풀을 적용해보고 동시성 처리를 경험한다.

### 배운 내용과 트러블 슈팅 내용을 정리하는 곳 
- [JWP에서 새로 배우는 내용들 정리하기](https://kimsy8979.notion.site/JWP-255a8022e0d7403c8032e760296bfc91?pvs=4)

### 시작 가이드
1. 미션을 시작하기 전에 파일, 입출력 스트림 학습 테스트를 먼저 진행합니다.
   - [File, I/O Stream](study/src/test/java/study)
   - 나머지 학습 테스트는 다음 강의 시간에 풀어봅시다.
2. 학습 테스트를 완료하면 LMS의 1단계 미션부터 진행합니다.

## 학습 테스트
1. [File, I/O Stream](study/src/test/java/study)
2. [HTTP Cache](study/src/test/java/cache)
3. [Thread](study/src/test/java/thread)


## 2단계 - HTTP 서버 구현하기 요구사항 목록
- HTTP 요청 구조에 따라 모든 데이터 읽어들이기
    -  ![img.png](img/img.png)
    - [x] startLine 파싱
      - 먼저 파싱해서 만든다 
    - [x] 모든 header 파싱 
      - 빈 줄이 나올 때 까지 파싱해서 만든다
    - ~~body 파싱 (2단계에선 요구되지 않는다)~~
      - 이는 POST일 경우에만 파싱하면 될 것 같다. (HttpMethod에 따라 body를 파싱)
      - POST일 경우, Header에서 Content-Length를 조회하고, 나머지 부분들에 대해 Length만큼만 읽는다. 
    - 특징 : headers와 body는 emptyLine으로 구분된다. 
    - [참고 자료](https://developer.mozilla.org/ko/docs/Web/HTTP/Messages)
- `GET /index.html`에 응답하기 
  - [x] `Http11ProcessorTest`의 모든 테스트 성공하기
- `CSS 지원하기`
  - [x] 사용자가 페이지를 열었을 때 `CSS 파일도 호출`하도록 기능 추가하기
  - [x] Content-Type이 `text/css`로 응답되어야 한다. 
- `Query String` 파싱하기 
  - [x] Query String을 파싱하기 
  - [x] 로그인 페이지에 접속했을 때 파싱한 ID, PW 값이 일치하면 콘솔 창에 로그로 회원을 조회한 결과가 나와야 한다


## 3단계 - 로그인 구현하기 요구사항 목록 
- 로그인 여부에 따라 다른 페이지로 이동시키기  
  - [x] 로그인 성공 시 HttpStatusCode 302를 반환하고 `/index.html`로 리다이렉트 
  - [x] 로그인 실패 시 `401.html`로 리다이렉트 
  - [x] 로그인 버튼을 눌렀을 때 `POST` 방식으로 전송하도록 변경한다 
    - [x] HTTP 요청 메세지에서 body를 파싱해와야 한다. 
- 회원 가입 
  - [x] `http://localhost:8080/register` 에 `GET` 메서드로 접속하면 회원 가입 페이지(`register.html`)를 보여준다
  - [x] 회원 가입 버튼을 누르면 `POST` 방식으로 데이터를 전송한다 
  - [x] 회원 가입이 완료되면 `/index.html`로 리다이렉트 한다  
- 로그인 성공 시, 쿠키와 세션을 활용해 로그인 상태를 유지해야 한다 
  - 로그인에 성공하면 응답 헤더에 `Set-Cookie` 를 추가하고, `JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46` 형태로 값을 전달하면, 클라이언트 요청 헤더의 `Cookie` 필드에 값이 추가된다
    ```text
    GET /index.html HTTP/1.1
    Host: localhost:8080
    Connection: keep-alive
    Accept: */*
    Cookie: yummy_cookie=choco; tasty_cookie=strawberry; JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46
    ```
  - [] 로그인 성공 시 `Set-Cookie` 헤더에 `JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46` 값을 추가한다 
  - [] Cookie 클래스를 추가한다 
  - [] HTTP 요청 헤더의 Cookie에 `JESSIONID`가 없으면 HTTP 응답 헤더에 `Set-Cookie`를 반환 해준다 
- 세션 구현 
  - [] 쿠키에서 전달 받은 `JESSIONID` 값으로 로그인 여부를 체크한다 
  - [] 로그인에 성공하면 Session 객체의 값으로 `User`객체를 저장한다 
  - [] 로그인 상태에서 `/login` 페이지에 `GET` 요청으로 접근 시 `index.html`로 리다이렉트
