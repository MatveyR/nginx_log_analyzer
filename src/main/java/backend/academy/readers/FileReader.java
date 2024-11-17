package backend.academy.readers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileReader implements Reader {
    private Path path;

    @Override
    public List<String> read() {
        List<String> strings = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(String.valueOf(path));
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            while (bufferedReader.ready()) {
                strings.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error while reading a file by path %s.", path), e);
        }
        return strings;
    }
}
