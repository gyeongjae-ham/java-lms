package nextstep.courses.domain;

public enum SessionStatus {
    PREPARE,
    REGISTER,
    CLOSE,
    ;

    public static boolean isNotRegister(SessionStatus other) {
        return !(REGISTER == other);
    }
}
