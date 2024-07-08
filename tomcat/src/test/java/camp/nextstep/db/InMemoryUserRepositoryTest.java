package camp.nextstep.db;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryUserRepositoryTest {

    @Test
    void find_by_correct_account() {
        var gugu = InMemoryUserRepository.findByAccount("gugu");

        assertThat(gugu.isEmpty()).isFalse();
    }

    @Test
    void find_by_wrong_account() {
        var wooyu = InMemoryUserRepository.findByAccount("woo-yu");

        assertThat(wooyu.isEmpty()).isTrue();
    }

}