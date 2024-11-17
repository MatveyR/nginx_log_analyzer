import backend.academy.readers.DirectoryReader;
import backend.academy.readers.HttpReader;
import backend.academy.readers.Reader;
import backend.academy.readers.ReaderFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReaderTest {
    private static final String FILE_PATH = "src/main/resources/test_logs/hidden_logs.txt";

    @Test
    void testValidHttpPath() {
        // given
        String path =
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

        // when
        Reader reader = ReaderFactory.getReader(path);

        // then
        assertInstanceOf(HttpReader.class, reader);
    }

    @Test
    void testGetDirectoryReaderWithValidPath() {
        // given
        String path = "src/**/hidden_logs.txt";

        // when
        Reader reader = ReaderFactory.getReader(path);

        // then
        assertInstanceOf(DirectoryReader.class, reader);
        assertEquals(1, ((DirectoryReader) reader).getPaths().size());
        assertEquals(FILE_PATH, ((DirectoryReader) reader).getPaths().getFirst().toString());
    }

    @Test
    void testGetDirectoryReaderWithAnotherValidPath() {
        // given
        String path = "src/**/test_logs/*";

        // when
        Reader reader = ReaderFactory.getReader(path);

        // then
        assertInstanceOf(DirectoryReader.class, reader);
        assertEquals(1, ((DirectoryReader) reader).getPaths().size());
        assertEquals(FILE_PATH, ((DirectoryReader) reader).getPaths().getFirst().toString());
    }

    @Test
    void testGetDirectoryReaderWithInvalidPath() {
        // given
        String path = "nonexistent/path/*.log";

        // when and then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReaderFactory.getReader(path);
        });
        assertEquals("No such file.", exception.getMessage());
    }
}
