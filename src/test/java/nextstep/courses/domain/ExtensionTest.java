package nextstep.courses.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExtensionTest {

    @DisplayName("문자열이 주어지면 확장자에 있는지 검증한다")
    @Test
    void verify() {
        boolean result = Extension.verify("png");
        assertThat(result).isTrue();
    }
}
