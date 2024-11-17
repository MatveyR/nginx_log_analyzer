package backend.academy.readers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HttpReader implements Reader {
    private URI uri;

    @Override
    public List<String> read() {
        HttpRequest request = getRequest();
        return getResponse(request);
    }

    private HttpRequest getRequest() {
        return HttpRequest
            .newBuilder()
            .uri(uri)
            .GET()
            .build();
    }

    private List<String> getResponse(HttpRequest request) {
        List<String> response;
        try {
            response = HttpClient
                .newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofLines())
                .body()
                .toList();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(String.format("Error while reading a file %s.", uri.toString()), e);
        }
        return new ArrayList<>(response);
    }
}
