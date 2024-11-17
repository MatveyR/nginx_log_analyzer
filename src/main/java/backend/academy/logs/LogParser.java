package backend.academy.logs;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings({"LineLength"})
public class LogParser {
    private static final Pattern NGINX_REGEX =
        Pattern.compile(
            "(\\S*) - (\\S*) \\[(\\d{2}/[A-Z][a-z]+/\\d{4}(?::\\d{2}){3} [+-]\\d{4})] \"([A-Z]+) (\\S*) \\S*\" (\\d{3}) (\\d+) \"(.*)\" \"(.*)\"");
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
    private static final int ADR = 1;
    private static final int USER = 2;
    private static final int DATE = 3;
    private static final int TYPE = 4;
    private static final int RES = 5;
    private static final int CODE = 6;
    private static final int SIZE = 7;
    private static final int REF = 8;
    private static final int AG = 9;

    public static Log parse(String stringLog) {
        Matcher matcher = NGINX_REGEX.matcher(stringLog);
        if (!matcher.find()) {
            throw new IllegalArgumentException(String.format("NGINX log was not in correct format: %s", stringLog));
        }
        String address = matcher.group(ADR);
        String user = matcher.group(USER);
        OffsetDateTime date = OffsetDateTime.parse(matcher.group(DATE), DATE_TIME_FORMATTER);
        String requestType = matcher.group(TYPE);
        String resource = matcher.group(RES);
        int httpCode = Integer.parseInt(matcher.group(CODE));
        int size = Integer.parseInt(matcher.group(SIZE));
        String referer = matcher.group(REF);
        String userAgent = matcher.group(AG);
        return new Log(address, user, date, requestType, resource, httpCode, size, referer, userAgent);
    }
}
