package backend.academy.writers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("MagicNumber")
public class WriterUtils {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;
    public static final Map<Integer, String> HTTP_STATUS_DESCRIPTIONS = new HashMap<>();

    public static Map<Integer, String> getHttpStatusDescriptions() {
        HTTP_STATUS_DESCRIPTIONS.put(100, "Continue");
        HTTP_STATUS_DESCRIPTIONS.put(101, "Switching Protocols");
        HTTP_STATUS_DESCRIPTIONS.put(102, "Processing");

        HTTP_STATUS_DESCRIPTIONS.put(200, "OK");
        HTTP_STATUS_DESCRIPTIONS.put(201, "Created");
        HTTP_STATUS_DESCRIPTIONS.put(202, "Accepted");
        HTTP_STATUS_DESCRIPTIONS.put(203, "Non-Authoritative Information");
        HTTP_STATUS_DESCRIPTIONS.put(204, "No Content");
        HTTP_STATUS_DESCRIPTIONS.put(205, "Reset Content");
        HTTP_STATUS_DESCRIPTIONS.put(206, "Partial Content");
        HTTP_STATUS_DESCRIPTIONS.put(207, "Multi-Status");
        HTTP_STATUS_DESCRIPTIONS.put(208, "Already Reported");
        HTTP_STATUS_DESCRIPTIONS.put(226, "IM Used");

        HTTP_STATUS_DESCRIPTIONS.put(300, "Multiple Choices");
        HTTP_STATUS_DESCRIPTIONS.put(301, "Moved Permanently");
        HTTP_STATUS_DESCRIPTIONS.put(302, "Found");
        HTTP_STATUS_DESCRIPTIONS.put(303, "See Other");
        HTTP_STATUS_DESCRIPTIONS.put(304, "Not Modified");
        HTTP_STATUS_DESCRIPTIONS.put(305, "Use Proxy");
        HTTP_STATUS_DESCRIPTIONS.put(307, "Temporary Redirect");
        HTTP_STATUS_DESCRIPTIONS.put(308, "Permanent Redirect");

        HTTP_STATUS_DESCRIPTIONS.put(400, "Bad Request");
        HTTP_STATUS_DESCRIPTIONS.put(401, "Unauthorized");
        HTTP_STATUS_DESCRIPTIONS.put(402, "Payment Required");
        HTTP_STATUS_DESCRIPTIONS.put(403, "Forbidden");
        HTTP_STATUS_DESCRIPTIONS.put(404, "Not Found");
        HTTP_STATUS_DESCRIPTIONS.put(405, "Method Not Allowed");
        HTTP_STATUS_DESCRIPTIONS.put(406, "Not Acceptable");
        HTTP_STATUS_DESCRIPTIONS.put(407, "Proxy Authentication Required");
        HTTP_STATUS_DESCRIPTIONS.put(408, "Request Timeout");
        HTTP_STATUS_DESCRIPTIONS.put(409, "Conflict");
        HTTP_STATUS_DESCRIPTIONS.put(410, "Gone");
        HTTP_STATUS_DESCRIPTIONS.put(411, "Length Required");
        HTTP_STATUS_DESCRIPTIONS.put(412, "Precondition Failed");
        HTTP_STATUS_DESCRIPTIONS.put(413, "Payload Too Large");
        HTTP_STATUS_DESCRIPTIONS.put(414, "URI Too Long");
        HTTP_STATUS_DESCRIPTIONS.put(415, "Unsupported Media Type");
        HTTP_STATUS_DESCRIPTIONS.put(416, "Range Not Satisfiable");
        HTTP_STATUS_DESCRIPTIONS.put(417, "Expectation Failed");
        HTTP_STATUS_DESCRIPTIONS.put(421, "Misdirected Request");
        HTTP_STATUS_DESCRIPTIONS.put(422, "Unprocessable Entity");
        HTTP_STATUS_DESCRIPTIONS.put(423, "Locked");
        HTTP_STATUS_DESCRIPTIONS.put(424, "Failed Dependency");
        HTTP_STATUS_DESCRIPTIONS.put(426, "Upgrade Required");
        HTTP_STATUS_DESCRIPTIONS.put(428, "Precondition Required");
        HTTP_STATUS_DESCRIPTIONS.put(429, "Too Many Requests");
        HTTP_STATUS_DESCRIPTIONS.put(431, "Request Header Fields Too Large");
        HTTP_STATUS_DESCRIPTIONS.put(451, "Unavailable For Legal Reasons");

        HTTP_STATUS_DESCRIPTIONS.put(500, "Internal Server Error");
        HTTP_STATUS_DESCRIPTIONS.put(501, "Not Implemented");
        HTTP_STATUS_DESCRIPTIONS.put(502, "Bad Gateway");
        HTTP_STATUS_DESCRIPTIONS.put(503, "Service Unavailable");
        HTTP_STATUS_DESCRIPTIONS.put(504, "Gateway Timeout");
        HTTP_STATUS_DESCRIPTIONS.put(505, "HTTP Version Not Supported");
        HTTP_STATUS_DESCRIPTIONS.put(506, "Variant Also Negotiates");
        HTTP_STATUS_DESCRIPTIONS.put(507, "Insufficient Storage");
        HTTP_STATUS_DESCRIPTIONS.put(508, "Loop Detected");
        HTTP_STATUS_DESCRIPTIONS.put(510, "Not Extended");
        HTTP_STATUS_DESCRIPTIONS.put(511, "Network Authentication Required");

        return HTTP_STATUS_DESCRIPTIONS;
    }

    public static void deleteIfExist(String pathStr) {
        Path path = Path.of(pathStr);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new RuntimeException("Error while deleting existing file.", e);
            }
        }
    }
}
