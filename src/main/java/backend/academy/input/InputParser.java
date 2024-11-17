package backend.academy.input;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.time.OffsetDateTime;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InputParser {
    public static Input parse(String[] args) {
        Args parsedArgs = new Args();
        JCommander commander = JCommander.newBuilder()
            .addObject(parsedArgs)
            .build();

        try {
            commander.parse(args);
        } catch (ParameterException e) {
            throw new IllegalArgumentException("The path was not specified", e);
        }

        String inputPath = parsedArgs.path;
        OffsetDateTime inputFrom = getDate(parsedArgs.from);
        OffsetDateTime inputTo = getDate(parsedArgs.to);
        FileFormat inputFormat = getFormat(parsedArgs.format);

        return new Input(inputPath, inputFrom, inputTo, inputFormat);
    }

    private static OffsetDateTime getDate(String date) {
        if (date != null) {
            return OffsetDateTime.parse(date + "T00:00:00+00:00");
        }
        return null;
    }

    private static FileFormat getFormat(String formatString) {
        if (formatString == null) {
            return null;
        }
        return switch (formatString) {
            case "markdown" -> FileFormat.MARKDOWN;
            case "adoc" -> FileFormat.ADOC;
            default -> throw new IllegalArgumentException("Incorrect format of file");
        };
    }

    private static class Args {
        @Parameter(names = {"-p", "--path"}, required = true)
        private String path;

        @Parameter(names = {"-f", "--from"})
        private String from;

        @Parameter(names = {"-t", "--to"})
        private String to;

        @Parameter(names = {"-fmt", "--format"})
        private String format;
    }
}
