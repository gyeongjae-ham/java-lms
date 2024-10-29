package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

public class StudentsTest {

    @DisplayName("학생수 제한을 넘어서면 등록이 안된다")
    @Test
    void check_student_limit() {
        List<NsUser> students = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students studentList = new Students(students, 2);

        assertThatThrownBy(() -> studentList.add(NsUserTest.GYEONGJAE))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("무료강의라 제한이 없는 경우 추가가 성공한다")
    @Test
    void register_on_free_session() {
        List<NsUser> students = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students studentList = new Students(students);

        studentList.add(NsUserTest.GYEONGJAE);

        assertThat(studentList.size()).isEqualTo(3);
    }
}
