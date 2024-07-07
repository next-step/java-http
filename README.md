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


## 2단계 - HTTP 서버 구현하기 요구사항 목록
- HTTP 요청 구조에 따라 모든 데이터 읽어들이기
    -  ![img.png](img/img.png)
    - [] startLine 파싱
      - 먼저 파싱해서 만든다 
    - [] 모든 header 파싱 
      - 빈 줄이 나올 때 까지 파싱해서 만든다
    - [] body 파싱 
      - 이는 POST일 경우에만 파싱하면 될 것 같다. (HttpMethod에 따라 body를 파싱)
      - POST일 경우, Header에서 Content-Length를 조회하고, 나머지 부분들에 대해 Length만큼만 읽는다. 
    - 특징 : headers와 body는 emptyLine으로 구분된다. 
    - [참고 자료](https://developer.mozilla.org/ko/docs/Web/HTTP/Messages)
- `GET /index.html`에 응답하기 
  - [] `Http11ProcessorTest`의 모든 테스트 성공하기
- `CSS 지원하기`
  - [] 사용자가 페이지를 열었을 때 `CSS 파일도 호출`하도록 기능 추가하기
  - [] Content-Type이 `test/css`로 응답되어야 한다. 
- `Query String` 파싱하기 
  - [] Query String을 파싱하기 
  - [] 로그인 페이지에 접속했을 때 파싱한 ID, PW 값이 일치하면 콘솔 창에 로그로 회원을 조회한 결과가 나와야 한다