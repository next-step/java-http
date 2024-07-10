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

### 요구사항 1 - GET 요청
- HTTP GET 요청에 대한 RequestLine을 파싱한다.
- 파싱하는 로직 구현을 TDD로 구현한다.
- 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
   - method는 GET
   - path는 /users
   - protocol은 HTTP
   - version은 1.1

### 요구사항 2 - POST 요청
- HTTP POST 요청에 대한 RequestLine을 파싱한다.
- 파싱하는 로직 구현을 TDD로 구현한다.
- 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
   - method는 POST
   - path는 /users
   - protocol은 HTTP
   - version은 1.1

### 요구사항 3 - Query String 파싱
- HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
- 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
- 파싱하는 로직 구현을 TDD로 구현한다.