package org.apache.coyote;

public enum Protocol {
    HTTP("1.1");

    public final String version;

    Protocol(String version) {
        this.version = version;
    }

}
