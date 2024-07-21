package camp.nextstep.http.domain.response;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileResponseBody implements HttpResponseBody {
    private File file;

    public FileResponseBody(File file) {
        this.file = file;
    }

    @Override
    public String getBodyString() {
        if (file == null) {
            return null;
        }

        String fileStr = "NOT FOUND";
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             final Stream<String> lines = bufferedReader.lines()) {
            fileStr = lines.collect(Collectors.joining("\r\n"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileStr;
    }
}
