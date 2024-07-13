package camp.nextstep.response;

public enum ResponseWritingStage {
    BEFORE_RESPONSE_LINE,
    ADDING_HEADERS,
    WROTE_CONTENT,
    FINISHED,
}
