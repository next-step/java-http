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

- [ ] HTTP 서버 구현
    - [X] GET /index.html 응답
        - [X] Http11ProcessorTest 모든 테스트 통과
        - [X] HttpRequestHeader 구현
            - [X] RequestLine 포함
    - [ ] CSS 지원
        - [ ] resources/static/css내의 css 파일 호출
    - [ ] QueryString 파싱
        - [ ] 로그인 페이지 호출시 입력한 QueryString의 아이디, 비밀번호가 일치하면 콘솔창에 회원 조회 결과 로깅
