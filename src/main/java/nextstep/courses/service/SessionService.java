package nextstep.courses.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nextstep.courses.domain.Image;
import nextstep.courses.domain.ImageRepository;
import nextstep.courses.domain.Images;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionImage;
import nextstep.courses.domain.SessionImageRepository;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStudent;
import nextstep.courses.domain.SessionStudentRepository;
import nextstep.courses.domain.Students;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;

@Service("sessionService")
public class SessionService {

    @Resource(name = "sessionRepository")
    private SessionRepository sessionRepository;

    @Resource(name = "sessionStudentRepository")
    private SessionStudentRepository sessionStudentRepository;

    @Resource(name = "sessionImageRepository")
    private SessionImageRepository sessionImageRepository;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "imageRepository")
    private ImageRepository imageRepository;

    @Transactional
    public Session findSessionWithId(long sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(NotFoundException::new);

        List<Long> studentIds = sessionStudentRepository.findAllBySessionId(sessionId).stream()
            .map(SessionStudent::getStudentId)
            .collect(Collectors.toList());
        List<NsUser> studentList = new ArrayList<>();
        for (Long studentId : studentIds) {
            studentList.add(userRepository.findById(studentId).orElseThrow(NotFoundException::new));
        }

        List<Long> imageIds = sessionImageRepository.findAllBySessionId(sessionId).stream()
            .map(SessionImage::getImageId)
            .collect(Collectors.toList());
        List<Image> imageList = new ArrayList<>();
        for (Long imageId : imageIds) {
            imageList.add(imageRepository.findById(imageId).orElseThrow(NotFoundException::new));
        }

        session.addStudents(new Students(sessionId, studentList));
        session.addImages(new Images(sessionId, imageList));

        return session;
    }
}
