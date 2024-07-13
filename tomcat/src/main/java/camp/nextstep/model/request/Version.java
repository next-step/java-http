package camp.nextstep.model.request;

public enum Version {
    VERSION_1_0("1.0"),
    VERSION_1_1("1.1"),
    VERSION_2_0("2.0"),
    ;

    private String value;

    Version(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Version fromString(String versionString) {
        for (Version version : Version.values()) {
            if (version.value.equals(versionString)) {
                return version;
            }
        }
        return null;
    }
}
