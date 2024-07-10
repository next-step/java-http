package support;

import java.io.File;

public class StubFile extends File {
    private final String name;

    public StubFile(final String fileName) {
        super(fileName);
        this.name = fileName;
    }

    @Override
    public String getName() {
        return name;
    }
}
