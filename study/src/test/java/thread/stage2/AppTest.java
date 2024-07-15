package thread.stage2;

import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    private static final AtomicInteger count = new AtomicInteger(0);

    /**
     * 1. App 클래스의 애플리케이션을 실행시켜 서버를 띄운다.
     * 2. 아래 테스트를 실행시킨다.
     * 3. AppTest가 아닌 App의 콘솔에서 SampleController가 생성한 http call count 로그를 확인한다.
     * 4. application.yml에서 설정값을 변경해보면서 어떤 차이점이 있는지 분석해본다.
     * - 로그가 찍힌 시간
     * - 스레드명(nio-8080-exec-x)으로 생성된 스레드 갯수를 파악
     * - http call count
     * - 테스트 결과값
     */
    @Test
    void test() throws Exception {
        final var NUMBER_OF_THREAD = 5;
        var threads = new Thread[NUMBER_OF_THREAD];
        // 5번 요청한다.
        for (int i = 0; i < NUMBER_OF_THREAD; i++) {
            threads[i] = new Thread(() -> incrementIfOk(TestHttpUtils.send("/test")));
        }

        for (final var thread : threads) {
            thread.start();  // 작업 처리
            Thread.sleep(50); // MAIN 스레드 SLEEP
        }

        for (final var thread : threads) {
            thread.join();
        }

        assertThat(count.intValue()).isEqualTo(5);
        // 테스트 결과 스레드 풀 개수가 5개 일때, 배운것에 따르면, 5개만 처리가 되어야하는데 스레드풀 개수보다 많은 6개가 처리됨
        //    accept-count: 10 # work queue 사이즈
        //    max-connections: 20 # 최대 맺을 수 있는 커넥션 수 -> 동시 수용 가능한 요청 수
        //    threads:
        //      min-spare: 1 # 영향 없음
        //      max: 5 # 스레드 풀 개수
        // 결론:
        // 1. 요청을 빠르게 처리하고 다시 사용 가능한 상태
        // 2. 혹은 TOMCAT의 NIO 사용으로 인한 1 쓰레드 1 요청 처리가 아닐 수도 있음
    }

    private static void incrementIfOk(final HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            count.incrementAndGet();
        }
    }
}
