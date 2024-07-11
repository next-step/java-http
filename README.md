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

## 1단계 - TDD 실습
- request example
```http request
GET /docs/index.html HTTP/1.1
Host: www.nowhere123.com
Accept: image/gif, image/jpeg, */*
Accept-Language: en-us
Accept-Encoding: gzip, deflate
User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)
(blank line)
```

- RequestLine
  - RequestLine에서 각 요소가 존재하지 않으면 예외가 발생한다.
    - 공백으로 분리했을 때 값이 3개가 아니면 예외가 발생한다.
  - HttpMethod를 파싱할 수 있다.
  - path을 파싱할 수 있다.
  - protocol과 버전을 파싱할 수 있다.
    - protocol 값과 version이 없으면 예외가 발생한다.
- HttpPath
  - ?를 통해 queryString을 파싱할 수 있다.
  - queryString의 경우 &로 각 값이 구분된다.
  - queryString의 각 값은 =를 통해 key와 value로 구분된다.
  - =를 통해 각 값이 정상적으로 분지어지지 않는 경우 예외가 발생한다.
- HttpMethod
  - GET과 POST를 가진다.
  - GET과 POST가 아닌 값으로 파싱하려하는 경우 예외가 발생한다.
- HttpProtocol
  - HttpProtocol 스펙(protocol, version)을 관리한다. 
  - protocol값과 version이 없으면 예외가 발생한다.

## 2단계 - HTTP 서버 구현하기
### 1. GET /index.html 응답하기
- inputStream의 첫줄은 RequestLine으로 저장한다.
- 입력된 값에서 파일정보가 있는 경우 html 파일로 파싱하여 response body를 반환한다.
- root의 경우 default로 "hello world"를 출력하도록 한다.

- FileUtil
  - resource가 없는 경우 예외가 발생한다.
  - 요청된 경로에 있는 resource에 있는 데이터를 반환한다.
  - file path를 받아 파일 확장자를 반환할 수 있다.

### 2. CSS 지원하기
- css파일인 경우 응답헤더의 content-type을 text/css로 반환한다.
  - 요청값의 확장자를 가지고 css를 판단한다. 
- html파일인 경우 응답헤더의 content-type을 text/html로 반환한다.

- ContentType
  - 확장자에 맞는 content-type으로 변환할 수 있다.
    - html: text/html
    - css: text/css
    - js: application/javascript

### 3. Query String 파싱
- /login을 요청했을 때 query string을 파싱하여 로그를 남긴다.

## 3단계 - 로그인 구현하기
### 1. HTTP Status Code 302
- HttpResponse
  - status코드를 가진다(200 ok, 302 ok)
    - found의 경우 location을 받아 생성한다.
    - ok의 경우 contentType을 받아 생성한다.
  - response header를 가진다
  - response body를 가진다
    - response body의 content length를 header에 저장한다
    - body가 비어있을 경우 content length와 content type이 없이 반환된다.
  - response format에 맞추어 응답값을 반환한다
- /login에서 gugu/password로 로그인에 성공하면 302로 반환하고 /index.html로 리다이랙할 수 있도록 전달한다.
```
HTTP/1.1 302 Found
Location: /index.html
```
- 로그인에 성공할 경우 `/index.html`로 리다이랙하고 실패하는 경우 `/401.html`로 리다이랙하도록 한다.

### 2. POST 방식으로 회원가입
- `http://localhost:8080/register` 호출 시
  - GET 요청인 경우 회원가입 페이지를 보여준다.
  - POST 요청인 경우 회원가입을 한 후 `/index.html`로 리다이랙한다.
- 로그인 페이지도 동일하게 버튼 클릭 시 POST 방식으로 변경한다.

- HttpRequest
  - RequestLine
  - HttpRequestHeader
    - List값을 읽어 포멧에 맞게 파싱하여 생성할 수 있다.
    - Content-Length가 존재하는지 확인하고 가져올 수 있다.
  - HttpRequestBody
- HttpRequestBody
  - `Content-Type`이 `application/x-www-form-urlencoded`인 경우 &로 구분된 값을 파싱하여 저장한다.

### 3. Cookie에 JSESSIONID 값 저장하기
- 쿠키 헤더에 있는 값을 읽어 저장한다.
  - session id를 추출한다 (없으면 예외가 발생한다.)
- 쿠키 포멧에 맞지 않게 값이 입력되면 예외가 발생한다.
- response header에 cookie를 저장할 수 있다.

### 4. Session 구현하기
- 로그인에 성공하면 session을 쿠키에 넣어 반환한다.
- 이미 로그인한 상태에서 `/login`에 들어오는 경우 index.html로 리다이랙한다.
  - cookie에 저장된 session이 있는 session인지 확인할 수 있다.
- 세션을 관리할 수 있다.

## 4단계 - 리팩터링
### 3. Controller 인터페이스 추가하기
- RequestMapping
  - HttpRequest를 받아 path를 정리하여 처리할 Controller를 결정한다.
- Controller
  - HttpRequest 요청을 받아 처리한다.
  - Get과 Post를 직접 확인하여 처리할 수 있다.
    - 지원하지 않는 경우 기본적으로 `/404.html`을 리턴할 수 있도록 한다.
  - LoginController, RootController, RegisterController, DefaultPathController
    - Http11Processor에 구현된 값을 가져와 구현해 리팩터링한다.

## 5단계 - 동시성 확장하기
- `acceptCount`와 `maxThreads`는 각각 어떤 설정일까?
  - acceptCount : 웹 요청에서 실행가능한 스레드가 생길때까지 대기할 수 있는 요청 수. 모든 스레드가 실행중이면 acceptCount가 올라감
  - coreThreads : 기본적으로 유지되는 유휴 스레드
  - maxThreads : 어플리케이션 내에서 최대로 실행가능한 단위(스레드). 유휴 스레드수가 넘어가면 maxThreads 갯수까지 스레드를 생성하여 실행 
- 최대 ThradPool의 크기는 250, 모든 Thread가 사용 중인(Busy) 상태이면 100명까지 대기 상태로 만들려면 어떻게 할까?
  - `ThreadPoolExecutor`를 직접 정의하고 queueCapacity로 100인 `ArrayBlockingQueue`를 구성
  - 실행 중인 스레드가 maxThread가 되면 해당 queue에 쌓이게되고 대기가 100개가 넘어가면 `AbortPolicy`로 핸들링
