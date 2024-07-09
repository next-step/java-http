package study;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StringTest {
    @Test
    void test() {
        assertThat("a".split("=", 2)).isEqualTo(new String[]{"a"});
        assertThat("a=".split("=", 2)).isEqualTo(new String[]{"a", ""});
    }

    @Test
    void conversionTest() {
        Object str1 = "";
        Object str2 = "abc";

        assertThat(String.valueOf(str1)).isEqualTo("");
        assertThat(String.valueOf(str2)).isEqualTo("abc");
    }

    @Test
    void substringTest() {
        String keyAndValue = "abc=";

        int index = keyAndValue.indexOf("=");
        assertThat(keyAndValue.substring(0, index)).isEqualTo("abc");
        assertThat(keyAndValue.substring(index + 1)).isEqualTo("");
    }
}
