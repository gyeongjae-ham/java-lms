package nextstep.courses.app;

import nextstep.courses.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static nextstep.Fixtures.aSession;
import static nextstep.Fixtures.aSessionRegistration;
import static nextstep.users.domain.NsUserTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class SessionJoinServiceTest {
    @Autowired
    private SessionJoinService sessionJoinService;

    @Autowired
    private SessionJoinRepository sessionJoinRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    @DisplayName("강의 등록")
    void test01() {
        long savedSessionId = getSavedSessionId(aSessionRegistration());

        sessionJoinService.register(savedSessionId, List.of(JAVAJIGI.getUserId()));

        List<SessionJoin> findSessionJoins = sessionJoinRepository.findAllBySessionId(savedSessionId);
        assertThat(findSessionJoins).hasSize(1);
    }

    @Test
    @DisplayName("강의 등록 - 최대 강의 수 초과")
    void test02() {
        Session session = aSession().withSessionStatus(SessionStatus.PROGRESS)
                                    .withSessionRegistration(aSessionRegistration().withMaxUserCount(1).build())
                                    .build();
        long savedSessionId = sessionRepository.save(session);
        Session findSession = sessionRepository.findById(savedSessionId);
        findSession.register(SAERANG, SessionJoinStatus.APPROVAL);
        sessionJoinRepository.save(findSession);

        assertThatThrownBy(() -> sessionJoinService.register(savedSessionId, List.of(JAVAJIGI.getUserId(), SANJIGI.getUserId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("수강 신청 승인")
    void test11() {
        long savedSessionId = getSavedSessionId(aSessionRegistration().withMaxUserCount(2));
        sessionJoinService.register(savedSessionId, List.of(JAVAJIGI.getUserId()));

        sessionJoinService.approve(savedSessionId, List.of(JAVAJIGI.getUserId()));

        List<SessionJoin> sessionJoins = sessionJoinRepository.findAllBySessionId(savedSessionId);
        assertThat(sessionJoins).hasSize(1)
                                .extracting("session.id", "nsUser.id", "sessionJoinStatus")
                                .containsExactly(tuple(savedSessionId, JAVAJIGI.getId(), SessionJoinStatus.APPROVAL));
    }

    @Test
    @DisplayName("수강 신청 승인 - 인원 초과")
    void test12() {
        long savedSessionId = getSavedSessionId(aSessionRegistration().withMaxUserCount(1));
        sessionJoinService.register(savedSessionId, List.of(JAVAJIGI.getUserId()));

        assertThatThrownBy(() -> sessionJoinService.approve(savedSessionId, List.of(JAVAJIGI.getUserId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("수강 신청 거절")
    void test13() {
        long savedSessionId = getSavedSessionId(aSessionRegistration().withMaxUserCount(1));
        sessionJoinService.register(savedSessionId, List.of(JAVAJIGI.getUserId()));

        sessionJoinService.reject(savedSessionId, List.of(JAVAJIGI.getUserId()));

        List<SessionJoin> sessionJoins = sessionJoinRepository.findAllBySessionId(savedSessionId);
        assertThat(sessionJoins).hasSize(1)
                                .extracting("session.id", "nsUser.id", "sessionJoinStatus")
                                .containsExactly(tuple(savedSessionId, JAVAJIGI.getId(), SessionJoinStatus.REJECTION));
    }

    private long getSavedSessionId(SessionRegistrationBuilder withMaxUserCount) {
        Session session = aSession().withId(1L)
                                    .withSessionStatus(SessionStatus.PROGRESS)
                                    .withSessionRegistration(withMaxUserCount.build())
                                    .build();
        return sessionRepository.save(session);
    }
}