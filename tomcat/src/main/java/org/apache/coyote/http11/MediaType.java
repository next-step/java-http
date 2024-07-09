package org.apache.coyote.http11;

import java.io.File;

public enum MediaType {
        HTML(".html", "text/html"),
        CSS(".css", "text/css"),
        TEXT("", "text/plain")
        ;

        private final String fileExtension;
        private final String value;

        MediaType(final String fileExtension, final String value) {
            this.fileExtension = fileExtension;
            this.value = value;
        }

        public static MediaType from(final File file) {
            String fileName = file.getName();
            if (fileName.endsWith(HTML.fileExtension)) {
                return HTML;
            }

            if (fileName.endsWith(CSS.fileExtension)) {
                return CSS;
            }

            return TEXT;
        }

        public String getValue() {
            return value;
        }
    }