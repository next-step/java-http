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

---

### 2단계 미션

- [X] GET /index.html 응답하기
  - [X] line이 null인 경우에 예외 처리를 해준다
- [X] CSS 지원하기
  - [X] CSS인 경우 응답 헤더의 Content-Type을 text/css로 전송한다.
- [X] Query String 파싱
  - [X] http request의 첫 번째 라인에서 request uri를 추출한다
  - [ ] login.html 파일에서 태그에 name을 추가해준다.
  - [X] 추출한 request uri에서 접근 경로와 이름=값으로 전달되는 데이터를 추출해서 User 객체를 조회하자.
  - [X] InMemoryUserRepository를 사용해서 미리 가입되어 있는 회원을 조회하고 로그로 확인해보자.




### 3단계 미션

- [X] HTTP Status Code 302
  - [X] 로그인에 성공하면 응답 헤더에 http status code를 302로 반환하고 /index.html로 리다이렉트 한다.
  - [X] 로그인에 실패하면 401.html로 리다이렉트한다.
- [ ] POST 방식으로 회원가입
  - [X] http://localhost:8080/register으로 접속하면 회원가입 페이지(register.html)를 반환한다
  - [X] 회원가입 페이지 요청은 GET을 사용한다.
  - [X] 회원가입을 버튼을 누르면 HTTP method를 GET이 아닌 POST를 사용한다.
  - [X] 회원가입을 완료하면 index.html로 리다이렉트한다.
  - [X] 로그인 페이지도 버튼을 눌렀을 때 GET 방식에서 POST 방식으로 전송하도록 변경한다
- [ ] Cookie에 JSESSIONID 값 저장하기
  - [X] 세션을 구현하기 전에 먼저 쿠키를 구현해본다.
  - ex) 서버에서 HTTP 응답을 전달할 때 응답 헤더에 Set-Cookie를 추가하고 JSESSIONID=656cef62-e3c4-40bc-a8df-94732920ed46 형태로 값을 전달하면 클라이언트 요청 헤더의 Cookie 필드에 값이 추가된다.
  - [X] Cookie 클래스를 추가하고 HTTP Request Header의 Cookie에 JSESSIONID가 없으면 HTTP Response Header에 Set-Cookie를 반환해주는 기능을 구현한다.
  - [X] 로그인에 성공하면 쿠키와 세션을 활용해서 로그인 상태를 유지해야 한다.
  - [X] HTTP 서버는 세션을 사용해서 서버에 로그인 여부를 저장한다.

- [X] Session 구현하기
  - [X] 쿠키에서 전달 받은 JSESSIONID의 값으로 로그인 여부를 체크할 수 있어야 한다.
  - [X] 로그인에 성공하면 Session 객체의 값으로 User 객체를 저장한다
  - [X] 그리고 로그인된 상태에서 /login 페이지에 HTTP GET method로 접근하면 이미 로그인한 상태니 index.html 페이지로 리다이렉트 처리한다.


### 4단계 미션

- [ ] 1. HttpRequest 클래스 구현하기
- [ ] 2. HttpResponse 클래스 구현하기
- [ ] 3. Controller 인터페이스 추가하기