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
- HttpMethod
  - GET과 POST를 가진다.
  - GET과 POST가 아닌 값으로 파싱하려하는 경우 예외가 발생한다.
- HttpProtocol
  - HttpProtocol 스펙(protocol, version)을 관리한다. 
  - protocol값과 version이 없으면 예외가 발생한다.