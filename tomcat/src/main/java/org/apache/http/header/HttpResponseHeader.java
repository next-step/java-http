package org.apache.http.header;

public interface HttpResponseHeader {
    String DELIMITER = ": ";

    @Override
    String toString();
}
