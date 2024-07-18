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

### 프로그래밍 요구사항

- 모든 로직에 단위테스트 구현
- 자바 코드 컨벤션 지키면서 프로그래밍
- 한 메서드에 한 단계의 들여쓰기만 한다.

### 기능목록 및 commit 로그 요구사항

- feat (feature)
- fix (bug fix)
- docs (documentation)
- style (formatting, missing semi colons, …)
- refactor
- test (when adding missing tests)
- chore (maintain)

## 기능 요구 사항

### 1. Executors로 Thread Pool 적용

Connector 클래스의 void process(final Socket connection) 메서드에서 요청마다 스레드를 새로 생성하고 있다.
Connector 클래스에서 Executors 클래스를 사용해서 ExecutorService 객체를 만들어보자.
스레드 갯수는 maxThreads라는 변수로 지정한다.

```
// maxThreads를 추가했다.
public Connector(final Container container, final int port, final int acceptCount, final int maxThreads) {
   // 생성자에서 스레드 풀 생성
}
```

#### 생각해보기 🤔

- acceptCount와 maxThreads는 각각 어떤 설정일까?
    - https://tomcat.apache.org/tomcat-9.0-doc/config/http.html#:%7E:text=Description-,acceptCount,-The%20maximum%20length
    - maxThreads?
        - 이 Connector 에서 생성할 처리 스레드 수입니다. 요청을 동시에 처리할 수 있는 최대 수를 의미합니다.
        - 지정하지 않으면 이 속성은 200으로 설정됩니다.

    - acceptCount?
        - 최대 연결 수에 도달했을 때 들어오는 연결 요청에 대해 OS 에서 제공하는 대기열의 최대 길이입니다.
        - OS 는 이 설정을 무시할 수 있습니다.
        - 이 대기열이 가득 차면 추가 연결을 거부하거나 타임아웃 발생시킬 수 있습니다.
        - 기본값은 100입니다.


- maxThreads? [Q]
- 최대 ThreadPool 의 크기는 250, 모든 Thread 가 사용 중인(Busy) 상태이면 100명까지 대기 상태로 만들려면 어떻게 할까?
    - maxThreads 를 250, acceptCount 를 100 으로 지정하면 250개 동시요청에 100의 대기상태를 허용할 수 있습니다.

### 2. 동시성 컬렉션 사용하기

`SessionManager` 클래스에서 `Session` 컬렉션은 여러 스레드가 동시에 접근할 수 있다.
그러다보니 `Session` 컬렉션에 여러 스레드가 동시에 접근하여 읽고 쓰다보면 스레드 안정성을 보장하기 어렵다.
동시성 컬렉션(Concurrent Collections)을 적용해서 스레드 안정성과 원자성을 보장해보자.
