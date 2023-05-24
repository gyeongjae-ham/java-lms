package nextstep.session.domain;

import nextstep.session.NotRecruitException;
import nextstep.session.StudentNumberExceededException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {

    @Test
    void 강의_수강신청은_모집중일_때만_가능하다() {

        // given
        Session session = new Session(1L, 1L, SessionStatus.END);

        // when
        assertThatThrownBy(
                () -> session.signUp(NsUserTest.JAVAJIGI))
                .isInstanceOf(NotRecruitException.class);
    }

    @Test
    void 강의는_강의_최대_수강_인원을_초과할_수_없다() {

        // given
        Session session = new Session(1L, 0L, SessionStatus.RECRUITING);

        // when
        assertThatThrownBy(
                () -> session.signUp(NsUserTest.JAVAJIGI))
                .isInstanceOf(StudentNumberExceededException.class);
    }
}