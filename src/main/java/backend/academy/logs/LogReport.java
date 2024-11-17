package backend.academy.logs;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LogReport {
    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;
    private final long totalAmount;
    private final int averageSize;
    private final List<Map.Entry<String, Long>> mostPopularResources;
    private final List<Map.Entry<Integer, Long>> mostPopularAnswers;
    private final List<Map.Entry<String, Long>> mostPopularTypes;
    private final long maxAmountRequestsPerDay;
    private final LocalDate dayWithMaxAmountRequests;
}
