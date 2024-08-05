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


## 1단계 요구사항 정리
![fileImage](./image/filetest.png)
![fileImage](./image/requirement1test.png)
- [x] 사전요구사항 - 학습테스트
  - [x] FILE, I/0 STREAM
  - [x] HTTP Cache
  - [x] Thread
- [x] 요구사항 1 - GET 요청
   - [x] GET 요청 RequestLine 파싱
     - [x] Method
     - [x] path
     - [x] protocol
     - [x] version
     - [x] TEST 생성
- [x] 요구사항 2 - POST 요청
  - [x] GET 요청 RequestLine 파싱
    - [x] Method
    - [x] path
    - [x] protocol
    - [x] version
    - [x] TEST 생성
- [x] 요구사항 3 - QUERY STRING 파싱 
  - [x] HTTP 요청 QUERY STRING 데이터 파싱
  - [x] TEST 생성
- [x] 요구사항 4 - ENUM 적용
  - [x] Method 인 GET, POST 적용
- [x] 요구사항 5 - 공통 요구사항
  - [x] HTTP REQUEST 클래스 설계
  - [x] HTTP REQUEST를 파싱하는 책임을 분리
  - [x] 커스텀 예외 생성

## 2단계 요구사항 정리
- [x] 리뷰 반영 
  - [x] Response에 미구현된 것 확인
  - [x] Review 질문 답변
  - [x] GreetingController에 'index.html'에 suffix가 있어야 하는 이유
  - [x] 동시성 문제 코멘트에 대한 정리
  - [x] 상수 네이밍 확인 DELIMITER
  - [x] STREAM 닫지 못한것 확인
  - [x] String.SPLIT PATTERN 객체 생성 주입
  - [x] 코드간 스타일 (상수 + 변수 개행)
- [x] 요구사항 1 - GET /index.html 응답하기
  - [x] Resource를 반환하는 기능 지원
- [x] 요구사항 2 - GET CSS 지원하기
  - [x] Request에 header 추가로 읽어오기 (Content-Type 추가)
  - [x] Response에 header 추가하기 (Content-Type 추가)
- [x] 요구사항 3 - GET QUERY 파싱하기
  - [x] InMemoryUserRepository 사용해서 회원 조회
- [x] 요구사항 4 - 공통 설계 요구사항
  - [x] HTTP RESPONSE를 생성하는 책임을 분리 (Factory)
  - [x] HTTP RESPONSE 도메인 객체 생성 + HEADER 추가
  - [x] HTTP Request에 따른 Resource 맵핑 하는 책임을 가진 ControllerFactoryProvider 생성
  - [x] ControllerFactoryProvider가 url 맵핑 리소스들을 스캐닝해와서 HttpResponse를 만드는 Factory와 연결한다. 
  - [x] Factory가 RequestMethod에 따라 Strategy를 구성해서 Response를 반환합니다.
  - [x] Response Test 생성
  - [x] Request Test 생성

- [x] 피드백 반영
  - [x] 홈페이지 접속 확인
    - STREAM 닫지 못한것 확인 -> Stream을 Manual하게 닫아줌
    - 해당 inputStream을 닫으면서, outputstream도 닫히는 이슈입니다. (try-with-resources로 같이 선언됨)
    - Buffer를 바로 생성해서 Parser로 넘겨주어서 try-with-resources를 사용할 수 있게 변경하였습니다.
  - [x] 사용하지 않는 커멘트 제거
  - [x] log.error 제거하고 log.info로 변경하였습니다.
  - [x] 404 일 경우 null 반환 확인
  - [x] resource static 디렉토리 허용으로 변경하기
  - [x] ContentType 에 대한 enum 생성하기
  - [x] null 반환하는 함수들 제거하기


## 3단계 요구사항 정리
- [x] 요구사항 1 - HTTP Status Code 302
  - [x] 성공 케이스: 302 -> /index.html
  - [x] 실패 케이스: 401.html
- [x] 요구사항 2 - POST 방식으로 회원가입
  - [x] Post 되는것 확인하기
  - [x] Parser 에서 request body parse 하기
  - [x] User 생성하기
- [x] 요구사항 3 - Cookie에 JSESSIONID 값 저장하기
  - [x] HTTP Request Header 구현하기
  - [x] HTTP Request Cookie 구현하기
  - [x] HTTP Response Cookie 구현하기
  - [x] 각각 테스트 하기
- [x] 요구사항 4 - Session 구현하기
  - [x] SessionManager 구현하기
    - [x] 로그인 성공시 Session 객체로 User 저장
    - [x] 이미 로그인시 /login 페이지는 index.html로 리다이렉트 처리한다.
- [x] 공통 요구사항
  - [x] readAllBytes 의 OOM 이슈로 제거하기
  - [x] OOM 을 하지 않는 구현에 대한 테스트 생성하기
  - [x] strategy -> factory provider GET 과 POST에 따라 재구성하기
  - [x] 구성요소에 따른 패키지 분리하기

## 4단계 요구사항 정리
- [x] 요구사항 1 - HttpRequest 클래스 구현하기 
  - [x] 테스트도 추가하기
- [x] 요구사항 2 - HttpResponse 클래스 구현하기
  - [x] 테스트도 추가하기
- [x] 요구사항 3 - Controller 인터페이스 추가하기
  - [x] Controller Interface 주입하기
- [x] 피드백 사항
  - [x] 사용되지 않는 코드 삭제
  - [x] 프로바이더 객체에서 구현체 정보 입력 -> Config 레이어 생성하기 및 Tomcat에 provider 건네주기 (의존성 분리)
  - [x] 팩토리 명명법 변경하기
  - [x] 블럭 생략 제거
  - [x] query parsing 책임 strategy에 있는 부분 변경하기
  - [x] 상수와 변수 개행
  - [x] Response Header 와 Response 생성로직 추상화 -> HTTP ENTITY
  - [x] queryString 에서 map으로 파싱하는 로직을 리팩토링
- [x] 추가 피드백 사항
  - [x] set-cookie 일차함수로 리팩토링
    - consumer로 strategy 패턴을 구현할 수 있음
    - template 패턴을 구현할 수 있음
    - supplier로 lazy evaluator로 content-length 같은 값을 설정할 수 있음
  - [x] ControllerConfig 가 n번 불렸을때, n개의 객체가 계속 재생성 되는 이슈
  - [x] 인터페이스 이름과 구현체 같은 이름 제거
  - [x] Matcher의 matches, find 다른점 확인
    - matches는 전체의 String에서 regex 패턴을 매칭합니다.
    - 그와 별개로 find는 Substring 에서 Matching 을 확인합니다. -> /sdagagdex.dsifjas/index 
      - 이 경우에 find는 /index와 매칭이 됩니다.
  - [x] HttpResponse의 빌더 사용해서 축약적 사용생성
  - [x] factories 예외 컨트롤러?? 확장성 제한됨
  - [x] Response에서 HttpEntity 흡수
  - [x] 반복되는 로직은 메서드 추출 (HttpEntity)

  - [] readAllBytes 의 OOM 이슈로 제거하기 -> 여전히 HEAP에 파일을 읽어서 올리게 되는데 INPUTSTREAM -> OUTPUTSTREAM 으로 이전하는 과정에서 CONTENT-LENGTH 측정 실패 


톰캣에선 다음과 같이 Response에서 write 할때, content-length를 측정하는것같은데 output stream 을 write 할때,
@Functional Interface 로 contentLength를 업데이트 해줄려고했음. -> 아쉽지만 실패하였음


    public void doWrite(ByteBuffer chunk) throws IOException {
    int len = chunk.remaining();
    outputBuffer.doWrite(chunk);
    contentWritten += len - chunk.remaining();
    }

        public long getBytesWritten(boolean flush) {
        if (flush) {
            try {
                outputBuffer.flush();
            } catch (IOException ioe) {
                // Ignore - the client has probably closed the connection
            }
        }
        return getCoyoteResponse().getBytesWritten(flush);
    }


## 5 단계 요구사항 정리
- [x] 요구사항 1 - Executors로 Thread Pool 적용
  - acceptCount 와 maxThreads 는 각각 어떤 설정인가?
  - 최대 ThreadPool 크기는 250, 모든 Thread Busy일때, 100명까지 대기상태로 만들려면?
- [x] 요구사항 2 - 동시성 컬렉션 사용하기
  - SessionManager 클래스에서 Session 컬렉션의 스레드 안정성과 원자성을 보장하자
- [x] 공통 요구사항
  - 쓰레드풀 이해
    - 대상 작업이 독립적인 작업 수행해야한다.
    - 어떻게 10개의 스레드가 풀을 만들까?
      - Executor로 ThreadPoolExecutor로 생성한다. (생성자)
    - 어떻게 풀에서 쓰레드를 하나 할당할까?
      - execute로 pool의 할당가능한 thread 확인후에 add worker로 task에 대한 Worker 객체를 만들고 태스크를 할당한다.
    - 쓰레드가 없을때, 태스크는 어떻게 될까?
      - WorkerQueue에 밀린 태스크를 넣는다. (execute)
    - future에 대한 값은?
      - ExecutorService 에서 Submit을 하게 되면 Callable인 FutureTask에 execute를 해준다.
  - newFixedThreadPool 소스 분석
    - Interface ScheduledExectuorService 를 구현한 ScheduledThreadPoolExecutor이 있습니다.
    - Executors 에서 newFixedThreadPool이라는 것으로 ThreadPoolExecutor으로 ThreadPool을 생성합니다.
    - ThreadPoolExecutor는 poolSize, maximumPoolSize, keepAliveTime, unit, workQueue를 받습니다.
  
