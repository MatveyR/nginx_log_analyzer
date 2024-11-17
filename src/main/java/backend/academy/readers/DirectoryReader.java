package backend.academy.readers;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DirectoryReader implements Reader {
    private final List<Path> paths;

    public List<Path> getPaths() {
        return paths;
    }

    @Override
    public List<String> read() {
        List<String> strings = new ArrayList<>();
        for (Path path : paths) {
            Reader fileReader = new FileReader(path);
            strings.addAll(fileReader.read());
        }
        return strings;
    }
}
