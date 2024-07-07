package camp.nextstep.request;

import java.io.BufferedReader;
import java.io.IOException;

// XXX: test
public class RequestParser {
    public Request parse(BufferedReader bufferedReader) throws IOException {
        return new Request(bufferedReader.readLine());
    }
}
