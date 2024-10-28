package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class FreeSession extends Session {

    private final Long id;
    private final Students students;

    public FreeSession(
        Long id,
        SessionDate sessionDate,
        SessionImage sessionImage,
        Type sessionType)
    {
        super(sessionDate,
            sessionImage,
            sessionType);
        this.id = id;
        this.students = new Students();
    }

    public void addStudent(NsUser student) {
        students.add(student);
    }
}
