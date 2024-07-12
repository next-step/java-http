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

---

### 2단계 미션

- [X] GET /index.html 응답하기
  - [X] line이 null인 경우에 예외 처리를 해준다
- [X] CSS 지원하기
  - [X] CSS인 경우 응답 헤더의 Content-Type을 text/css로 전송한다.
- [ ] Query String 파싱
  - [ ] http request의 첫 번째 라인에서 request uri를 추출한다
  - [ ] login.html 파일에서 태그에 name을 추가해준다.
  - [ ] 추출한 request uri에서 접근 경로와 이름=값으로 전달되는 데이터를 추출해서 User 객체를 조회하자.
  - [ ] InMemoryUserRepository를 사용해서 미리 가입되어 있는 회원을 조회하고 로그로 확인해보자.