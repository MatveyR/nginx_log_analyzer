package backend.academy.writers;

import backend.academy.input.Input;
import backend.academy.logs.LogReport;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WriterFactory {
    public static Writer getWriter(Input request, LogReport logReport) {
        if (request.format() == null) {
            return new TXTWriter(logReport);
        }
        switch (request.format()) {
            case ADOC -> {
                return new ADOCWriter(logReport);
            }
            case MARKDOWN -> {
                return new MDWriter(logReport);
            }
            default -> {
                return new TXTWriter(logReport);
            }
        }
    }
}
