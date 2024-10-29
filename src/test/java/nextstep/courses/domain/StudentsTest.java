package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;

public class StudentsTest {

    @DisplayName("최대 수강 학생 수와 현재 학생수를 비교한다")
    @Test
    void check_student_size_with_max_size() {
        List<NsUser> students = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students studentList = new Students(students);

        boolean result = studentList.isBigger(5);

        assertThat(result).isFalse();
    }

    @DisplayName("학생을 추가한다")
    @Test
    void register_on_session() {
        List<NsUser> students = List.of(
            NsUserTest.JAVAJIGI,
            NsUserTest.SANJIGI
        );
        Students studentList = new Students(students);

        studentList.add(NsUserTest.GYEONGJAE);

        assertThat(studentList.size()).isEqualTo(3);
    }
}
