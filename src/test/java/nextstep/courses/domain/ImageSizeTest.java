package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImageSizeTest {

    @DisplayName("width, height 크기가 범주 안이라면 생성된다")
    @Test
    void create() {
        ImageSize imageSize = new ImageSize(300, 200);
        assertThat(imageSize).isEqualTo(new ImageSize(300, 200));
    }

    @DisplayName("width 크기가 최소값 미만인 경우 예외가 발생한다")
    @Test
    void width_under_min_value() {
        assertThatThrownBy(() -> new ImageSize(12, 200))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("height 크기가 최소값 미만인 경우 예외가 발생한다")
    @Test
    void height_under_min_value() {
        assertThatThrownBy(() -> new ImageSize(300, 20))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("width와 height의 비율이 3:2가 아니면 예외가 발생한다")
    @Test
    void verify_width_height_rate() {
        assertThatThrownBy(() -> new ImageSize(300, 300))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
