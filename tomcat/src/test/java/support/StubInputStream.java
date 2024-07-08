package support;

import java.io.IOException;
import java.io.InputStream;

public class StubInputStream extends InputStream {
    private final String data;
    private int position;

    public StubInputStream(String data) {
        this.data = data;
        this.position = 0;
    }

    @Override
    public int read() throws IOException {
        if (position < data.length()) {
            return data.charAt(position++);
        } else {
            return -1; // End of stream
        }
    }

    @Override
    public int available() throws IOException {
        return data.length() - position;
    }
}
