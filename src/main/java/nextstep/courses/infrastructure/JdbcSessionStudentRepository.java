package nextstep.courses.infrastructure;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.courses.domain.SessionStudent;
import nextstep.courses.domain.SessionStudentRepository;
import nextstep.courses.domain.SessionStudentStatus;
import nextstep.courses.domain.Students;

@Repository("sessionStudentRepository")
public class JdbcSessionStudentRepository implements SessionStudentRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int[] saveAll(Students students) {
        String sql = "insert into session_student2 (session_id, student_id, session_student_status) values (?, ?, ?)";

        List<SessionStudent> sessionStudents = students.getStudents().stream()
            .map(it -> new SessionStudent(students.getSessionId(), it.getId()))
            .collect(Collectors.toList());

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SessionStudent sessionStudent = sessionStudents.get(i);
                ps.setLong(1, sessionStudent.getSessionId());
                ps.setLong(2, sessionStudent.getStudentId());
                ps.setString(3, sessionStudent.getSessionStudentStatus().toString());
            }

            @Override
            public int getBatchSize() {
                return sessionStudents.size();
            }
        });
    }

    @Override
    public List<SessionStudent> findAllBySessionId(long sessionId) {
        String sql = "select id, session_id, student_id, session_student_status from session_student2 where session_id = ?";

        RowMapper<SessionStudent> rowMapper = (rs, rowNum) -> new SessionStudent(
            rs.getLong("id"),
            rs.getLong("session_id"),
            rs.getLong("student_id"),
            SessionStudentStatus.from(rs.getString("session_student_status"))
        );

        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }
}