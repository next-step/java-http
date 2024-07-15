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

## HTTP 서버 구현하기

### 요구사항 1 - HTTP Status Code 302

- [ ] 로그인 성공시 HTTP Status Code 302를 반환하고, /index.html로 리다이렉트 한다.
- [ ] 로그인 실패시 HTTP Status Code 302를 반환하고, /401.html로 리다이렉트 한다.

### 요구사항 2 - POST 방식으로 회원가입

- [ ] http://localhost:8080/register으로 접속하면 회원가입 페이지(register.html)를 보여준다.
- [ ] 회원가입을 완료하면 index.html로 리다이렉트한다.

### 요구사항 3 - Cookie에 JSESSIONID 값 저장하기

- [ ] HTTP 응답 헤더에 Set-Cookie를 추가해 JSESSIONID 값을 반환한다.

### 요구사항 4 - Session 구현하기

- [ ] 로그인에 성공하면 Session 객체의 값으로 User 객체를 저장해보자.
- [ ] 로그인된 상태에서 /login 페이지에 접근시 index.html 페이지로 리다이렉트 처리한다.
