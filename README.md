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


## 컨벤션
### 커밋로그
커밋로그의 형태는 아래와 같이 맞춘다.

- 기능 구현
  - feat : "구현한 기능명"
- 버그 고치기
  - fix : "고친 내용"
- 문서 수정
  - docs : "수정한 문서 내용"
- 리팩토링
  - refactor : "리팩토링한 내용"
- 테스트 코드 작성
  - test : "테스트 내용"

## 구현할 기능
### RequestLine 파싱
- 여러 방식의 요청 대한 파싱을 구현
  - ex) GET /users HTTP/1.1 의 경우, 
    - method는 GET
    - path는 /users
    - protocol은 HTTP
    - version은 1.1
- HTTP요청으로 넘어오는 Query String 파싱
  - ex) name1=value1&name2=value2

