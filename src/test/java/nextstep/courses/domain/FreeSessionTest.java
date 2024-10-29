package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.users.domain.NsUserTest;

public class FreeSessionTest {

    @DisplayName("정상 데이터로 생성한 객체들이 같은지 비교한다")
    @Test
    void create() {
        FreeSession freeSession = SessionTestFixture.createFreeSession(SessionStatus.PREPARE);
        assertThat(freeSession).isEqualTo(SessionTestFixture.createFreeSession(SessionStatus.PREPARE));
    }

    @DisplayName("모집중이 아닌 상태라면 학생을 등록하지 못한다")
    @Test
    void addStudent() {
        FreeSession freeSession = SessionTestFixture.createFreeSession(SessionStatus.PREPARE);

        assertThatThrownBy(() -> freeSession.addStudent(NsUserTest.GYEONGJAE))
            .isInstanceOf(IllegalStateException.class);
    }
}
