# 🚀 1단계 - TDD 실습
## 0. 프로그래밍 요구사항
- 모든 로직에 단위 테스트를 구현한다. 테스트 하기 어려운 UI fhwlrdms wpdhlgksek.
- 자바 코드 컨벤션을 지키면서 프로그래밍한다.
- 한 메서드에 오직 한 단계의 들여쓰기만 한다.

## 1. RequestLine 을 파싱한다.
- RequestLine 을 파싱해 원하는 값을 가져올 수 있는 API 를 제공해야한다.
- RequestLine 은 HTTP 첫번째 라인을 의미한다.

### 요구사항 1 - GET 요청
- HTTP GET 요청에 대한 RequestLine을 파싱한다.
- 파싱하는 로직 구현을 TDD로 구현한다.
- 예를 들어 "GET /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
   - method는 GET
   - path는 /users
   - protocol은 HTTP
   - version은 1.1

### 요구사항 2 - POST 요청
- HTTP POST 요청에 대한 RequestLine을 파싱한다.
- 파싱하는 로직 구현을 TDD로 구현한다.
- 예를 들어 "POST /users HTTP/1.1"을 파싱하면 다음과 같은 결과를 얻을 수 있어야 한다.
   - method는 POST
   - path는 /users
   - protocol은 HTTP
   - version은 1.1

### 요구사항 3 - Query String 파싱
- HTTP 요청(request)의 Query String으로 전달되는 데이터를 파싱한다.
- 클라이언트에서 서버로 전달되는 데이터의 구조는 name1=value1&name2=value2와 같은 구조로 전달된다.
- 파싱하는 로직 구현을 TDD로 구현한다.

[Query String 예 - GET 요청]
`GET /users?userId=javajigi&password=password&name=JaeSung HTTP/1.1`

### 요구사항 4 - enum 적용 (선택)
- HTTP method인 GET, POST를 enum으로 구현한다.
