package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStatusTest {

    @DisplayName("상태가 같은지 검증한다")
    @Test
    void check_same_status() {
        boolean result = SessionStatus.REGISTER.isSame(SessionStatus.REGISTER);
        boolean result2 = SessionStatus.REGISTER.isSame(SessionStatus.PREPARE);

        assertThat(result).isTrue();
        assertThat(result2).isFalse();
    }
}
