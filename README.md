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
1. HTTP Status Code 302
- HttpResponse
  - status코드를 가진다(200 ok, 302 ok)
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
