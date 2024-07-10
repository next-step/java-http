package org.apache.file;

import org.apache.http.header.MediaType;

import java.nio.file.Path;

public record FileResource(Path path, String content, int contentSize, MediaType mediaType) {

}
