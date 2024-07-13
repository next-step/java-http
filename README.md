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

## 프로그래밍 요구사항 

- 모든 로직에 단위 테스트를 구현한다. 단, 테스트하기 어려운 UI 로직은 제외
- 자바 코드 컨벤션을 지키면서 프로그래밍한다.
- 규칙 1: 한 메서드에 오직 한 단계의 들여쓰기(indent)만 한다.

## 기능 목록 및 commit 로그 요구사항
- 기능을 구현하기 전에 README.md 파일에 구현할 기능 목록을 정리해 추가한다.
- git의 commit 단위는 앞 단계에서 README.md 파일에 정리한 기능 목록 단위로 추가한다.
- AngularJS Commit Message Conventions 을 지킨다.

## RequestLine 을 파싱한다.
- RequestLine 을 파싱해 원하는 값을 가져올 수 있는 API 를 제공해야한다.
- RequestLine 은 HTTP 첫번째 라인을 의미한다.

###  요구사항 1 - GET /index.html 응답하기
- 인덱스 페이지(http://localhost:8080/index.html)에 접근할 수 있도록 만들자.
  - Http11ProcessorTest 테스트 클래스의 모든 테스트를 통과하면 된다.
  - 첫 번째 라인(Request URI) 을 읽어오자
  - line이 null인 경우에 예외 처리를 해준다
  - http request의 첫 번째 라인에서 request uri를 추출한다.
  - 요청 url에 해당되는 파일을 resource 디렉토리에서 읽는다.
  - 브라우저에서 요청한 HTTP Request Header는 다음과 같다.

```
GET /index.html HTTP/1.1
Host: localhost:8080
Connection: keep-alive
Accept: */*
```

### 요구사항 2 -  CSS 지원하기
- 사용자가 페이지를 열었을 때 CSS 파일도 호출하도록 기능을 추가하자.
   - CSS인 경우 응답 헤더의 Content-Type을 text/css로 전송한다.
   - Content-Type은 확장자를 통해 구분할 수도 있으며, 요청 헤더의 Accept를 활용할 수도 있다.

```
GET /css/styles.css HTTP/1.1
Host: localhost:8080
Accept: text/css,*/*;q=0.1
Connection: keep-alive
```

### 요구사항 3 - Query String 파싱하기 

- http://localhost:8080/login?account=gugu&password=password 으로 접속하면 로그인 페이지(login.html)를 응답하자
- 그리고 로그인 페이지에 접속했을 때 Query String을 파싱해서 아이디, 비밀번호가 일치하면 콘솔창에 로그로 회원을 조회한 결과가 나오도록 만들자.
