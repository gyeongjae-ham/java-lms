package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionStatusTest {

    @DisplayName("상태가 같은지 검증한다")
    @Test
    void check_same_status() {
        boolean result = SessionStatus.isNotRegister(SessionStatus.REGISTER);
        boolean result2 = SessionStatus.isNotRegister(SessionStatus.PREPARE);

        assertThat(result).isFalse();
        assertThat(result2).isTrue();
    }
}
