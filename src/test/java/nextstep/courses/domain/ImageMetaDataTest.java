package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImageMetaDataTest {

    @Test
    void 생성() {
        ImageMetaData metaInfo = new ImageMetaData(1, Extension.JPG);

        assertThat(metaInfo).isEqualTo(new ImageMetaData(1, Extension.JPG));
    }

    @Test
    void 이미지_크기는_1MB_이하여야한다() {
        assertThatThrownBy(() -> new ImageMetaData(2, Extension.JPG))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
