package backend.academy.logs;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Log {
    private String address;
    private String user;
    private OffsetDateTime date;
    private String requestType;
    private String resource;
    private int httpCode;
    private int size;
    private String httpReferer;
    private String httpAgent;
}
