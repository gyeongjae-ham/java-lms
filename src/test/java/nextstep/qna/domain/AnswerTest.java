package nextstep.qna.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    public static final Answer A1 = new Answer(NsUserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(NsUserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    public void 답변삭제_테스트() {
        A1.deleted();
        assertThat(A1.isDeleted()).isTrue();
    }

    @Test
    public void 답변삭제시_히스토리생성테스트() {
        assertThat(A1.deleted()).isInstanceOf(DeleteHistory.class);
    }
}
