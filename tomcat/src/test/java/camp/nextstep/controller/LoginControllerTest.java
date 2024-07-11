package camp.nextstep.controller;

import camp.nextstep.http.domain.HttpMethod;
import camp.nextstep.http.domain.HttpRequest;
import camp.nextstep.http.domain.HttpSession;
import camp.nextstep.http.domain.HttpSessionManager;
import camp.nextstep.model.User;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import support.MockHttpRequestBuilder;
import support.MockHttpResponse;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.given;

class LoginControllerTest {

    public static final UUID mockedUUID = UUID.fromString("656cef62-e3c4-40bc-a8df-94732920ed46");

    private static MockedStatic<UUID> uuidMockedStatic;
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        loginController = new LoginController();
    }

    @BeforeAll
    static void beforeAll() {
        uuidMockedStatic = Mockito.mockStatic(UUID.class);
        given(UUID.randomUUID()).willReturn(mockedUUID);
    }

    @AfterAll
    public static void afterAll() {
        uuidMockedStatic.close();
    }

    @Test
    @DisplayName("로그인이 안되어 있을때 get 요청시 로그인 페이지 응답을 반환한다.")
    void getWithoutLoginTest() throws Exception {
        HttpSessionManager.clear();
        final HttpRequest httpRequest = new MockHttpRequestBuilder()
                .requestURI("/login")
                .method(HttpMethod.GET)
                .build();
        final MockHttpResponse httpResponse = MockHttpResponse.create();

        loginController.doGet(httpRequest, httpResponse);

        final URL resource = getClass().getClassLoader().getResource("static/login.html");
        final var expected = String.join(System.lineSeparator(),
                "HTTP/1.1 200 OK ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 3863 ",
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())));
        assertThat(httpResponse.getOutputAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("로그인이 되어 있을때 get 요청시 index 리다이렉트 응답을 반환한다.")
    void getWithLoginTest() throws Exception {
        final HttpSession session = new HttpSession(mockedUUID.toString());
        session.setAttribute("user", new User("test", "password", "email"));
        HttpSessionManager.add(session);
        final HttpRequest httpRequest = new MockHttpRequestBuilder()
                .requestURI("/login")
                .method(HttpMethod.GET)
                .addSessionCookie(mockedUUID.toString())
                .build();
        final MockHttpResponse httpResponse = MockHttpResponse.create();

        loginController.doGet(httpRequest, httpResponse);

        final var expected = String.join(System.lineSeparator(),
                "HTTP/1.1 302 Found ",
                "Location: /index.html ",
                "",
                "");
        assertSoftly(softly -> {
            softly.assertThat(httpResponse.getOutputAsString()).isEqualTo(expected);
            softly.assertThat(HttpSessionManager.computeIfAbsent(mockedUUID.toString())).isNotNull();
        });
    }

    @Test
    @DisplayName("post 요청을 처리할 수 있다.")
    void postTest() throws Exception {
        final HttpRequest httpRequest = new MockHttpRequestBuilder()
                .requestURI("/login")
                .method(HttpMethod.POST)
                .addBody("account", "gugu")
                .addBody("password", "password")
                .build();
        final MockHttpResponse httpResponse = MockHttpResponse.create();

        loginController.doPost(httpRequest, httpResponse);

        final var expected = String.join(System.lineSeparator(),
                "HTTP/1.1 302 Found ",
                String.format("Set-Cookie: JSESSIONID=%s ", mockedUUID),
                "Location: /index.html ",
                "",
                "");
        assertSoftly(softly -> {
            softly.assertThat(httpResponse.getOutputAsString()).isEqualTo(expected);
            softly.assertThat(HttpSessionManager.computeIfAbsent(mockedUUID.toString())).isNotNull();
        });
    }
}
