package nextstep.courses.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

enum Extension {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String extension;
    private static final Map<String, Extension> cachedExtensions = new HashMap<>();

    Extension(String extension) {
        this.extension = extension;
        cachedExtensions();
    }

    private void cachedExtensions() {
        Arrays.stream(Extension.values())
            .map(it -> cachedExtensions.put(it.extension, it));
    }

    public static boolean verify(String extension) {
        return cachedExtensions.get(extension) != null;
    }
}
