package nextstep.courses.domain;

public enum SessionStatus {
    PREPARE,
    REGISTER,
    CLOSE,
    ;

    public boolean isSame(SessionStatus other) {
        return this == other;
    }
}
