package nextstep.courses.domain;

import nextstep.courses.exception.InvalidDurationException;
import nextstep.courses.domain.type.SessionStatus;

import java.time.LocalDate;
import java.util.Objects;

public class Duration {

    private final LocalDate start;
    private final LocalDate end;

    public Duration(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
        validate();
    }

    private void validate() {
        validateNotNull();
        validateNotInvalid();
    }

    private void validateNotNull() {
        if (this.start == null || this.end == null) {
            throw new InvalidDurationException("기간을 입력해야합니다.");
        }
    }

    private void validateNotInvalid() {
        if (this.start.isAfter(this.end)) {
            throw new InvalidDurationException("종료일이 시작일 이전입니다.");
        }
    }

    public SessionStatus sessionStatus(LocalDate today) {
        if (today.isBefore(this.start)) {
            return SessionStatus.READY;
        }
        if (today.isBefore(this.end)) {
            return SessionStatus.RECRUITING;
        }
        return SessionStatus.TERMINATE;
    }

    public LocalDate start() {
        return this.start;
    }

    public LocalDate end() {
        return this.end;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duration)) return false;
        Duration duration = (Duration) o;
        return Objects.equals(start, duration.start) && Objects.equals(end, duration.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Duration{" +
            "start=" + start +
            ", end=" + end +
            '}';
    }

}