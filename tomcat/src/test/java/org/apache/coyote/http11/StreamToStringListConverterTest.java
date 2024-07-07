package org.apache.coyote.http11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class StreamToStringListConverterTest {

    static Stream<Arguments> argumentsStream() {
        final String multiLineString = String.join("\n",
                "😀😃😄😁😆😅😂🤣🥲☺️😊",
                "😇🙂🙃😉😌😍🥰😘😗😙😚",
                "😋😛😝😜🤪🤨🧐🤓😎🥸🤩",
                "");
        return Stream.of(
                Arguments.of("test", 1),
                Arguments.of("asd asdsad asdasd", 1),
                Arguments.of(multiLineString, 3)
        );
    }

    @DisplayName("string을 inputStream로 읽어 String 컬렉션으로 반환한다.")
    @ParameterizedTest
    @MethodSource("argumentsStream")
    void parsingTest(final String str, final int expectSize) throws Exception {
        // given
        final InputStream inputStream = new ByteArrayInputStream(str.getBytes());

        // when
        final List<String> result = StreamToStringListConverter.parseToStringList(inputStream);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(expectSize),
                () -> assertThat(result.contains(str.split("\n")[0])).isTrue()
        );
    }

    @DisplayName("InputStream이 null일시 IOException을 throw 한다.")
    @Test
    void throwIOExceptionTest() {
        // given // when // then
        assertThatThrownBy(() -> {
                    StreamToStringListConverter.parseToStringList(null);
                }
        ).isInstanceOf(IOException.class)
                .hasMessage("Input stream cannot be null");
    }
}
