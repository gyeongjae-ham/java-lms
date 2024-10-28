package nextstep.courses.domain;

import java.time.LocalDateTime;

class SessionTest {

    public static FreeSession createFreeSession() {
        SessionDate sessionDate = createSessionDate();
        SessionImage sessionImage = createSessionImage();

        return new FreeSession(1L, sessionDate, sessionImage, Type.FREE);
    }

    public static PaidSession createPaidSession(long price) {
        SessionDate sessionDate = createSessionDate();
        SessionImage sessionImage = createSessionImage();

        return new PaidSession(1L, sessionDate, sessionImage, Type.PAID, new Students(5), price);
    }

    public static SessionImage createSessionImage() {
        ImageSize imageSize = new ImageSize(300, 200);
        ImageMetaInfo imageMetaInfo = new ImageMetaInfo(1, Extension.JPG);
        return new SessionImage(imageSize, imageMetaInfo);
    }

    public static SessionDate createSessionDate() {
        LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2024, 10, 10, 10, 12);

        return new SessionDate(start, end);
    }
}
