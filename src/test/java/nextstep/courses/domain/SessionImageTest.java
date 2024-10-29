package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SessionImageTest {

    @Test
    void 생성() {
        ImageSize imageSize = new ImageSize(300, 200);
        ImageMetaData imageMetaData = new ImageMetaData(1, Extension.JPG);
        SessionImage sessionImage = new SessionImage(imageSize, imageMetaData);
        assertThat(sessionImage).isEqualTo(new SessionImage(imageSize, imageMetaData));
    }
}
