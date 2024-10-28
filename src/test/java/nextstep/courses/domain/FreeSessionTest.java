package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FreeSessionTest {

    @Test
    void 생성() {
        Session freeSession = SessionTest.createFreeSession();
        assertThat(freeSession).isEqualTo(SessionTest.createFreeSession());
    }
}
