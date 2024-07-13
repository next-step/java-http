package camp.nextstep.model.request;

import org.apache.commons.lang3.StringUtils;

public record Path(String value) {
    public static Path valueOf(String pathString) {
        return new Path(pathString);
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(value);
    }

    public boolean contains(String str) {
        return !isEmpty() && value.contains(str);
    }

    public String[] split(String str) {
        return value.split(str);
    }

    @Override
    public String toString() {
        return value;
    }
}
