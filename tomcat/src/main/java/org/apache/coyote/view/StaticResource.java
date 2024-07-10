package org.apache.coyote.view;

import org.apache.coyote.http.ContentType;

public class StaticResource {
    private final String content;
    private final ContentType mimeType;

    public StaticResource(final String content, final ContentType mimeType) {

        this.content = content;
        this.mimeType = mimeType;
    }

    public String getContent() {
        return content;
    }

    public ContentType getMimeType() {
        return mimeType;
    }
}
