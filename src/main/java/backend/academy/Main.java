package backend.academy;

import backend.academy.input.Input;
import backend.academy.input.InputParser;
import backend.academy.logs.Log;
import backend.academy.logs.LogAnalyzer;
import backend.academy.logs.LogParser;
import backend.academy.logs.LogReport;
import backend.academy.readers.Reader;
import backend.academy.readers.ReaderFactory;
import backend.academy.writers.Writer;
import backend.academy.writers.WriterFactory;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Main {
    public static void main(String[] args) {
        Input input = InputParser.parse(args);
        Reader reader = ReaderFactory.getReader(input.path());
        List<Log> logRecordStream = reader.read().stream().map(LogParser::parse).toList();
        LogReport report = new LogAnalyzer(logRecordStream, input).getReport();
        Writer writer = WriterFactory.getWriter(input, report);
        writer.write();
    }
}
