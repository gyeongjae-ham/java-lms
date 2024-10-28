package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

public class FreeSession extends Session {

    private final Long id;
    private final Students students;
    private final SessionStatus status;

    public FreeSession(
        Long id,
        SessionDate sessionDate,
        SessionImage sessionImage,
        SessionStatus sessionStatus,
        Type sessionType)
    {
        super(sessionDate,
            sessionImage,
            sessionType);
        this.id = id;
        this.students = new Students();
        this.status = sessionStatus;
    }

    public void addStudent(NsUser student) {
        if (!(status == SessionStatus.RESITER)) {
            throw new IllegalStateException("This session is not registering now");
        }
        students.add(student);
    }
}
