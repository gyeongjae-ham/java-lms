package nextstep.courses.infrastructure;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.domain.SessionTestFixture;
import nextstep.courses.domain.Type;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @DisplayName("Session 객체를 생성해서 저장하고 불러온다")
    @Test
    void crud() {
        Session session2 = new Session(1L, 1L, 3000L, SessionStatus.PREPARE, 5, SessionTestFixture.createSessionDate(),
            SessionTestFixture.createSessionImage(), Type.PAID);

        int count = sessionRepository.save(session2);
        assertThat(count).isEqualTo(1);

        Session savedSession2 = sessionRepository.findById(1L);

        assertThat(savedSession2.getPrice()).isEqualTo(3000L);
        LOGGER.debug("Session: {}", savedSession2);
    }
}
