package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

/**
 * 모집중인 상태에서는 수강 신청 가능 모집 중이라도 정원이 다 차면 수강 신청 불가능 종료된 상태에서는 수강 신청 불가능
 */
class SessionTest {

  public static Session ofPreparingSessionNoUsersYet() {
    return new Session(
        1L,
        SessionInfoTest.TEST_SESSION_INFO,
        ImageTest.TEST_IMAGE,
        SessionType.FREE,
        SessionStatus.PREPARING,
        SessionRecruitStatus.NOT_RECRUITING,
        StudentsTest.ofNoUsersYet(),
        SessionPeriodTest.TEST_SESSION_PERIOD);
  }

  public static Session ofRecruitingSessionLeftFewSeats() {
    return new Session(
        1L,
        SessionInfoTest.TEST_SESSION_INFO,
        ImageTest.TEST_IMAGE,
        SessionType.FREE,
        SessionStatus.IN_PROGRESS,
        SessionRecruitStatus.RECRUITING,
        StudentsTest.ofLeftFewSeats(),
        SessionPeriodTest.TEST_SESSION_PERIOD);
  }

  public static Session ofRecruitingSessionLeftOneSeat() {
    return new Session(
        1L,
        SessionInfoTest.TEST_SESSION_INFO,
        ImageTest.TEST_IMAGE,
        SessionType.FREE,
        SessionStatus.IN_PROGRESS,
        SessionRecruitStatus.RECRUITING,
        StudentsTest.ofLeftOneSeatUsers(),
        SessionPeriodTest.TEST_SESSION_PERIOD);
  }

  public static Session ofRecruitingSessionUserFull() {
    return new Session(
        1L,
        SessionInfoTest.TEST_SESSION_INFO,
        ImageTest.TEST_IMAGE,
        SessionType.FREE,
        SessionStatus.IN_PROGRESS,
        SessionRecruitStatus.RECRUITING,
        StudentsTest.ofFullUsers(),
        SessionPeriodTest.TEST_SESSION_PERIOD);
  }

  public static Session ofEndSessionFullUsers() {
    return new Session(
        1L,
        SessionInfoTest.TEST_SESSION_INFO,
        ImageTest.TEST_IMAGE,
        SessionType.FREE,
        SessionStatus.END,
        SessionRecruitStatus.NOT_RECRUITING,
        StudentsTest.ofFullUsers(),
        SessionPeriodTest.TEST_SESSION_PERIOD);
  }

  public Session ofInProgressSessionNotRecruiting() {
    return new Session(
        1L,
        SessionInfoTest.TEST_SESSION_INFO,
        ImageTest.TEST_IMAGE,
        SessionType.FREE,
        SessionStatus.IN_PROGRESS,
        SessionRecruitStatus.NOT_RECRUITING,
        StudentsTest.ofFullUsers(),
        SessionPeriodTest.TEST_SESSION_PERIOD);
  }



  @Test
  void Session이_진행중_비모집중_상태로_객체생성시_수강신청자가_없으면_Session_생성_성공_테스트() {
    assertThatNoException().isThrownBy(() ->
        new Session(
            1L,
            SessionInfoTest.TEST_SESSION_INFO,
            ImageTest.TEST_IMAGE,
            SessionType.FREE,
            SessionStatus.IN_PROGRESS,
            SessionRecruitStatus.NOT_RECRUITING,
            StudentsTest.ofNoUsersYet(),
            SessionPeriodTest.TEST_SESSION_PERIOD
        )
    );
  }

  @Test
  void Session이_진행중_비모집중_초기상태일때_수강신청자가_있으면_Session_생성_실패_테스트() {
    assertThatThrownBy(() ->
        new Session(
            1L,
            SessionInfoTest.TEST_SESSION_INFO,
            ImageTest.TEST_IMAGE,
            SessionType.FREE,
            SessionStatus.PREPARING,
            SessionRecruitStatus.NOT_RECRUITING,
            StudentsTest.ofLeftOneSeatUsers(),
            SessionPeriodTest.TEST_SESSION_PERIOD
        )
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("준비중 상태일때는 수강생이 없어야 합니다.");
  }

  @Test
  void Session이_준비중일때_수강신청을_하면_예외가_발생한다() {
    assertThatThrownBy(() ->
        ofPreparingSessionNoUsersYet().enroll(NsUserTest.JAVAJIGI)
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("준비중인 세션은 수강 신청할 수 없습니다.");
  }

  @Test
  void Session이_종료된_상테에서_수강신청을_하면_예외가_발생한다() {
    assertThatThrownBy(() ->
        ofEndSessionFullUsers().enroll(NsUserTest.JAVAJIGI)
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("종료된 세션은 수강 신청할 수 없습니다.");
  }



  @Test
  void Session이_진행중_모집중이고_아직_자리가_남아있을때_수강신청을_하면_예외가_발생하지_않는다() {
    assertThatNoException().isThrownBy(() ->
        ofRecruitingSessionLeftFewSeats().enroll(NsUserTest.JAVAJIGI)
    );
  }

  @Test
  void Session이_진행중_모집중이고_딱_한자리가_남은_경우라도_수강신청을_하면_예외가_발생하지_않는다() {
    assertThatNoException().isThrownBy(() ->
        ofRecruitingSessionLeftOneSeat().enroll(NsUserTest.SANJIGI)
    );
  }

  @Test
  void Session에_진행중_모집중_강의에_더_이상_수강생을_추가할_수_없는_상태에서_수강신청시_예외_발생() {
    assertThatThrownBy(() ->
        ofRecruitingSessionUserFull().enroll(NsUserTest.JAVAJIGI)
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("최대 인원수를 넘어서 수강 신청할 수 없습니다.");
  }


  @Test
  void Session이_진행중_비모집중인_강의에_수강신청을_하면_예외가_발생한다() {
    assertThatThrownBy(() ->
        ofInProgressSessionNotRecruiting().enroll(NsUserTest.JAVAJIGI)
    ).isInstanceOf(IllegalArgumentException.class)
        .hasMessage("비모집중인 세션은 수강 신청할 수 없습니다.");
  }


  @Test
  void Session이_진행중_모집중이고_수강신청을_하면_수강대기상태로_생성된다() {
    Session session = ofRecruitingSessionLeftFewSeats();

    session.enroll(NsUserTest.JAVAJIGI);

    int waitingStudentsSize = (int) session.getStudents().listOfStudents().stream()
        .filter(Student::isWaiting).count();

    assertThat(waitingStudentsSize).isEqualTo(1);
  }


}