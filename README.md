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

## 리팩터링

### 요구사항 1 - HttpRequest 클래스 구현하기

### 요구사항 2 - HttpResponse 클래스 구현하기

### 요구사항 3 - Controller 인터페이스 추가하기

- [ ] 컨트롤러 인터페이스를 추가하고 각 분기에 있는 로직마다 AbstractController를 상속한 구현체로 만들어보자.