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

## TO-DO List

### Mission 1

- [x] Request Line 파싱
    - [x] HTTP GET 또는 POST 요청에 대한 RequestLine 파싱
        - [x] method 파싱
        - [x] path 파싱
        - [x] protocol 파싱
        - [x] version 파싱
    - [X] Query String 파싱
        - [X] HTTP GET 요청시 path의 Query String 파싱
    - [x] Enum 적용
        - [x] GET, POST를 enum으로 구현

### Mission 2

- [X] HTTP 서버 구현
    - [X] GET /index.html 응답
        - [X] Http11ProcessorTest 모든 테스트 통과
        - [X] HttpRequestHeader 구현
            - [X] RequestLine 포함
    - [X] CSS 지원
        - [X] resources/static/css내의 css 파일 호출
    - [X] QueryString 파싱
        - [X] 로그인 페이지 호출시 입력한 QueryString의 아이디, 비밀번호가 일치하면 콘솔창에 회원 조회 결과 로깅

### Mission 3

- [X] 로그인 구현
    - [X] HTTP Status Code 302
        - [X] 로그인이 성공하면 302 Code를 반환하고, /index.html로 리다이렉트
        - [X] 로그인이 실패하면 302 Code를 반환하고, /401.html로 리다이렉트
    - [X] POST 방식으로 회원가입, 로그인
        - [X] 회원가입 페이지(register.html) GET 요청 조회
        - [X] 회원가입 버튼을 POST로 요청 /index.html로 리다이렉트
        - [X] 로그인도 POST 요청으로 리팩터링
    - [X] Cookie에 JSESSIONID 값 저장
        - [X] Cookie 클래스 추가
        - [X] Cookie에 JSESSIONID가 없으면 HTTP Response Header에 Set-Cookie를 반환
    - [X] Session 구현
        - [X] 로그인에 성공하면 Session 객체의 값으로 User 객체를 저장
        - [X] 로그인된 상태에서 /login 페이지에 HTTP GET method로 접근하면 index.html로 리다이렉트 처리

### Mission 4

- [X] HttpRequest 클래스 구현
- [X] HttpResponse 클래스 구현
- [X] Controller 인터페이스 추가
    - [X] 분기에 있는 로직마다 AbstractController를 상속한 구현체 구현
        - [X] LoginController 구현
        - [X] RegisterController 구현

### Mission 5

- [ ] ExecutorService를 이용한 스레드풀 구현
- [ ] 동시성 컬렉션 적용
