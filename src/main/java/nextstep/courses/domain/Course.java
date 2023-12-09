package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Course extends BaseEntity {
    private Long id;

    private String title;

    private Long creatorId;

    private Sessions sessions;

    private Course() {
    }

    public Course(String title, Long creatorId) {
        this(0L, title, creatorId, LocalDateTime.now(), null);
    }

    public Course(Long id, String title, Long creatorId) {
        this(id, title, creatorId, LocalDateTime.now(), null);
    }


    public Course(Long id, String title, Long creatorId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.title = title;
        this.creatorId = creatorId;
        this.sessions = new Sessions();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getCreatorId() {
        return creatorId;
    }


    public List<Session> sessions() {
        return sessions.values();
    }

    public void addSession(Session session) {
        sessions.addSession(session);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creatorId=" + creatorId +
                '}';
    }
}
