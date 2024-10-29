package nextstep.courses.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nextstep.users.domain.NsUser;

public class Students {

    private final Long sessionId;
    private final List<NsUser> students;

    public Students(Long sessionId) {
        this(sessionId, new ArrayList<>());
    }

    public Students(Long sessionId, List<NsUser> students) {
        this.sessionId = sessionId;
        this.students = new ArrayList<>(students);
    }


    public boolean isBigger(int studentMaxValue) {
        return studentMaxValue < this.students.size() + 1;
    }

    public void add(NsUser newStudent) {
        this.students.add(newStudent);
    }

    public int size() {
        return this.students.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Students)) {
            return false;
        }
        Students students1 = (Students)o;
        return Objects.equals(students, students1.students);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(students);
    }
}
