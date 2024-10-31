package nextstep.courses.infrastructure;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nextstep.courses.domain.Extension;
import nextstep.courses.domain.ImageMetaData;
import nextstep.courses.domain.ImageSize;
import nextstep.courses.domain.RegisterStatus;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionDate;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.Type;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "insert into new_session (course_id, price, session_status, register_status, max_student_size, start_at, end_at, byte_size, width, height, extension, type) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
            session.getCourseId(),
            session.getPrice(),
            session.getSessionStatus().toString(),
            session.getRegisterStatus().toString(),
            session.getMaxStudentSize(),
            session.getSessionDate().getStart(),
            session.getSessionDate().getEnd(),
            session.getSessionImage().getMetaInfo().getByteSize(),
            session.getSessionImage().getImageSize().getWidth(),
            session.getSessionImage().getImageSize().getHeight(),
            session.getSessionImage().getMetaInfo().getExtension().toString(),
            session.getSessionType().toString());
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, course_id, price, session_status, register_status, max_student_size, start_at, end_at, byte_size, width, height, extension, type from new_session where id = ?";

        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
                rs.getLong(1),
                rs.getLong(2),
                rs.getLong(3),
                SessionStatus.from(rs.getString(4)),
                RegisterStatus.from(rs.getString(5)),
                rs.getInt(6),
                new SessionDate(
                    toLocalDateTime(rs.getTimestamp(7)),
                    toLocalDateTime(rs.getTimestamp(8))
                ),
                new SessionImage(
                    new ImageSize(
                        rs.getInt(10),
                        rs.getInt(11)),
                    new ImageMetaData(
                        rs.getInt(9),
                        Extension.getWithString(rs.getString(12))
                    )
                ),
                Type.from(rs.getString(13))
            );

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
