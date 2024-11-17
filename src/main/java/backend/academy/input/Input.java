package backend.academy.input;

import java.time.OffsetDateTime;

public record Input(String path, OffsetDateTime from, OffsetDateTime to, FileFormat format) {
}
