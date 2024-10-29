package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionsTest {

    @DisplayName("정상적인 강의 목록을 가지고 일급콜렉션이 생성된다")
    @Test
    void create() {
        List<Session> sessionList = List.of(
            SessionTestFixture.createPaidSession(3000L, SessionStatus.PREPARE),
            SessionTestFixture.createFreeSession(SessionStatus.PREPARE)
        );
        Sessions sessions = new Sessions(sessionList);

        assertThat(sessions.size()).isEqualTo(2);
    }

    @DisplayName("해당하는 id의 Session을 반환한다")
    @Test
    void get_session_with_session_id() {
        List<Session> sessionList = List.of(
            SessionTestFixture.createPaidSession(3000L, SessionStatus.PREPARE),
            SessionTestFixture.createFreeSession(SessionStatus.PREPARE)
        );
        Sessions sessions = new Sessions(sessionList);

        Session session = sessions.getSession(2L);

        assertThat(session).isEqualTo(SessionTestFixture.createFreeSession(SessionStatus.PREPARE));
    }
}
