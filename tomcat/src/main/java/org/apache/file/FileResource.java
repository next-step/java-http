package org.apache.file;

import java.nio.file.Path;

public record FileResource(Path path, String content, int contentSize, MediaType mediaType) {

}
