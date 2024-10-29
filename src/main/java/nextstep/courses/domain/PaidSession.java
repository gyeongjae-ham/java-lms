package nextstep.courses.domain;

import java.util.Objects;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

public class PaidSession extends Session {

    private final Long id;
    private final Long courseId;
    private final Students students;
    private final Long price;
    private final SessionStatus status;
    private final Integer maxStudentSize;

    public PaidSession(
        long id,
        long courseId,
        SessionDate sessionDate,
        SessionImage sessionImage,
        Type type,
        Students students,
        Long price,
        Integer maxStudentSize)
    {
        this(id,
            courseId,
            sessionDate,
            sessionImage,
            SessionStatus.PREPARE,
            type,
            students,
            price,
            maxStudentSize);
    }

    public PaidSession(
        long id,
        long courseId,
        SessionDate sessionDate,
        SessionImage sessionImage,
        SessionStatus sessionStatus,
        Type type,
        Students students,
        Long price,
        Integer maxStudentSize)
    {
        super(sessionDate, sessionImage, type);
        this.id = id;
        this.courseId = courseId;
        this.students = students;
        this.price = price;
        this.status = sessionStatus;
        this.maxStudentSize = maxStudentSize;
    }

    public void register(Payment payment, NsUser student) {
        if (SessionStatus.isNotRegister(status)) {
            throw new IllegalStateException("This session is not registering now");
        }
        verifyUser(payment, student);
        checkValidSession(payment);
        checkValidPrice(payment);
        addStudent(student);
    }

    private void checkValidSession(Payment payment) {
        if (!payment.verifySession(this.id)) {
            throw new IllegalArgumentException("Session Info is not valid");
        }
    }

    private void checkValidPrice(Payment payment) {
        if (!payment.isSamePrice(this.price)) {
            throw new IllegalArgumentException("Price is not valid");
        }
    }

    private static void verifyUser(Payment payment, NsUser student) {
        if (!student.verifyId(payment)) {
            throw new IllegalArgumentException("Payment is not valid");
        }
    }

    public void addStudent(NsUser newStudent) {
        if (students.isBigger(this.maxStudentSize)) {
            throw new IllegalArgumentException("Max student size");
        }
        students.add(newStudent);
    }

    public int studentSize() {
        return this.students.size();
    }

    @Override
    public boolean compareId(Long sessionId) {
        return this.id == sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaidSession)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PaidSession that = (PaidSession)o;
        return Objects.equals(id, that.id) && Objects.equals(courseId, that.courseId) && Objects.equals(students,
            that.students) && Objects.equals(price, that.price) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, courseId, students, price, status);
    }
}
