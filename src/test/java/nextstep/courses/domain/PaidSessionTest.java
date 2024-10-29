package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUserTest;

class PaidSessionTest {

    @DisplayName("가격과 강의 상태를 가지고 유료 강의를 생성한다")
    @Test
    void create() {
        Session paidSession = SessionTestFixture.createPaidSession(3000L, SessionStatus.PREPARE);
        assertThat(paidSession).isEqualTo(SessionTestFixture.createPaidSession(3000L, SessionStatus.PREPARE));
    }

    @DisplayName("결제금액이 수강료와 같으면 수강신청된다")
    @Test
    void register() {
        Payment payment = new Payment("id", 1L, 1L, 3000L);
        PaidSession paidSession = SessionTestFixture.createPaidSession(3000L, SessionStatus.REGISTER);

        paidSession.register(payment, NsUserTest.JAVAJIGI);

        assertThat(paidSession.studentSize()).isEqualTo(1);
    }

    @DisplayName("모집중이 아니면 등록하지 못한다")
    @Test
    void register_throw_exception() {
        Payment payment = new Payment("id", 1L, 1L, 3000L);
        PaidSession paidSession = SessionTestFixture.createPaidSession(3000L, SessionStatus.PREPARE);

        assertThatThrownBy(() -> paidSession.register(payment, NsUserTest.JAVAJIGI))
            .isInstanceOf(IllegalStateException.class);
    }
}
