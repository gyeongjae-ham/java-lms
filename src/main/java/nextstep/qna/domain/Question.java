package nextstep.qna.domain;

import nextstep.qna.CannotDeleteException;
import nextstep.qna.UnAuthorizedException;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Question {
    private Long id;

    private String title;

    private String contents;

    private NsUser writer;

    private List<Answer> answers = new ArrayList<>();

    private Answers answers2 = new Answers(new ArrayList<>());

    private boolean deleted = false;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate;

    public Question() {
    }

    public Question(NsUser writer, String title, String contents) {
        this(0L, writer, title, contents);
    }

    public Question(Long id, NsUser writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Question setTitle(String title) {
        this.title = title;
        return this;
    }

    public NsUser getWriter() {
        return writer;
    }

    public void addAnswer(Answer answer) {
        answer.toQuestion(this);
        answers.add(answer);
    }

    public void addAnswer2(Answer answer) {
        answer.toQuestion(this);
        this.answers2 = answers2.add(answer);
    }

    public boolean isOwner(NsUser loginUser) {
        return writer.equals(loginUser);
    }

    public Question setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
    }

    public List<DeleteHistory> delete(NsUser loginUser) {
        List<DeleteHistory> deleteHistories = new ArrayList<>();

        checkWriter(loginUser);
        changeDeleteState(true);
        deleteHistories.add(new DeleteHistory(ContentType.QUESTION,this.id,this.writer));

        deleteHistories.addAll(answers2.delete(loginUser));

        return deleteHistories;
    }

    private void changeDeleteState(boolean state) {
        this.deleted = state;
    }

    private void checkWriter(NsUser loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException("질문을 삭제할 권한이 없습니다.");
        }
    }
}
