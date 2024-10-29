package nextstep.courses.domain;

import java.util.Objects;

public class ImageSize {
    public static final int MAX_WIDTH = 300;
    public static final int MAX_HEIGHT = 200;
    public static final int HEIGHT_RATE = 3;
    public static final int WIDTH_RATE = 2;

    private final int width;
    private final int height;

    public ImageSize(int width, int height) {
        if (width < MAX_WIDTH || height < MAX_HEIGHT) {
            throw new IllegalArgumentException();
        }
        checkRate(width, height);
        this.width = width;
        this.height = height;
    }

    private void checkRate(int width, int height) {
        if (calculateRate(width, height)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean calculateRate(int width, int height) {
        return !(HEIGHT_RATE * height == WIDTH_RATE * width);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageSize)) {
            return false;
        }
        ImageSize imageSize = (ImageSize)o;
        return width == imageSize.width && height == imageSize.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
