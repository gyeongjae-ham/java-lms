package nextstep.courses.repository;

import nextstep.courses.domain.Students;

public interface StudentsRepository {

    int save(Long id, Long sessionId);

    Students findBySessionId(Long id);
}
