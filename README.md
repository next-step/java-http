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
- [x] 사전요구사항 - 학습테스트
  - [x] FILE, I/0 STREAM
  - [x] HTTP Cache
  - [x] Thread
- [] 요구사항 1 - GET 요청
   - [] GET 요청 RequestLine 파싱
     - [] Method
     - [] path
     - [] protocol
     - [] version
     - [] TEST 생성
- [] 요구사항 2 - POST 요청
  - [] GET 요청 RequestLine 파싱
    - [] Method
    - [] path
    - [] protocol
    - [] version
    - [] TEST 생성
- [] 요구사항 3 - QUERY STRING 파싱 
  - [] HTTP 요청 QUERY STRING 데이터 파싱
  - [] TEST 생성
- [] 요구사항 4 - ENUM 적용
  - [] Method 인 GET, POST 적용
- [] 요구사항 5 - 공통 요구사항
  - [] HTTP REQUEST 클래스 설계
  - [] HTTP REQUEST를 파싱하는 책임을 분리
  - [] HTTP RESPONSE를 생성하는 책임을 분리
  - [] 커스텀 예외 생성
   

