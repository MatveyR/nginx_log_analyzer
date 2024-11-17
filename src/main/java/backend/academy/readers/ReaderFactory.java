package backend.academy.readers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("ParameterAssignment")
public class ReaderFactory {
    private static final Pattern HTTP_PATTERN = Pattern.compile(
            "^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*$");

    public static Reader getReader(String stringPath) {
        if (HTTP_PATTERN.matcher(stringPath).find()) {
            return getHttpReader(stringPath);
        }
        try {
            return new DirectoryReader(findFiles(".", stringPath));
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't find info to read.");
        }
    }

    private static HttpReader getHttpReader(String path) {
        try {
            return new HttpReader(new URI(path));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Incorrect url address.", e);
        }
    }

    public static List<Path> findFiles(String baseDir, String pattern) throws IOException {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        List<Path> matchingFiles = new ArrayList<>();

        Files.walkFileTree(Paths.get(baseDir), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                file = Path.of(file.toString().substring(2));
                if (matcher.matches(file)) {
                    matchingFiles.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        if (matchingFiles.isEmpty()) {
            throw new IllegalArgumentException("No such file.");
        }
        return matchingFiles;
    }
}
