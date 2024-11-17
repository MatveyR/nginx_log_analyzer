package backend.academy.logs;

import backend.academy.input.Input;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogAnalyzer {
    private static final int TOP = 3;
    private List<Log> logs;
    private Input request;

    public LogReport getReport() {
        getLogsWithNeededDate();
        OffsetDateTime startDate = request.from();
        OffsetDateTime endDate = request.to();
        long totalAmount = logs.size();
        int averageSize = getAverageSize();
        var mostPopularResources = getMostPopularResources();
        var mostPopularAnswers = getMostPopularAnswers();
        var mostPopularTypes = getMostPopularTypes();
        long maxAmountRequestsPerDay = getMaxAmountRequestsPerDay() == null ? 0 : getMaxAmountRequestsPerDay();
        var dayWithMaxAmountRequests = getDayWithMaxAmountRequests();
        return new LogReport(
            startDate,
            endDate,
            totalAmount,
            averageSize,
            mostPopularResources,
            mostPopularAnswers,
            mostPopularTypes,
            maxAmountRequestsPerDay,
            dayWithMaxAmountRequests
        );
    }

    private void getLogsWithNeededDate() {
        OffsetDateTime startDate = request.from() == null ? OffsetDateTime.MIN : request.from();
        OffsetDateTime endDate = request.to() == null ? OffsetDateTime.MAX : request.to();
        var logList = logs;
        List<Log> newLogList = new ArrayList<>();
        for (var log : logList) {
            if (!log.date().isBefore(startDate) && !log.date().isAfter(endDate)) {
                newLogList.add(log);
            }
        }
        logs = newLogList;
    }

    private int getAverageSize() {
        return ((int) logs.stream().mapToInt(Log::size).average().orElse(0));
    }

    private List<Map.Entry<String, Long>> getMostPopularResources() {
        Map<String, Long> map =
            logs.stream().collect(Collectors.groupingBy(Log::resource, Collectors.counting()));
        return getTop(map);
    }

    private List<Map.Entry<Integer, Long>> getMostPopularAnswers() {
        Map<Integer, Long> map =
            logs.stream().collect(Collectors.groupingBy(Log::httpCode, Collectors.counting()));
        return getTop(map);
    }

    private List<Map.Entry<String, Long>> getMostPopularTypes() {
        Map<String, Long> map =
            logs.stream().collect(Collectors.groupingBy(Log::requestType, Collectors.counting()));
        return getTop(map);
    }

    public static <K> List<Map.Entry<K, Long>> getTop(Map<K, Long> map) {
        List<Map.Entry<K, Long>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        List<Map.Entry<K, Long>> finalList = new ArrayList<>();
        for (int i = 0; i < Math.min(list.size(), TOP); ++i) {
            finalList.add(list.get(list.size() - i - 1));
        }
        return finalList;
    }

    private Long getMaxAmountRequestsPerDay() {
        if (groupAndSortByDate().isEmpty()) {
            return null;
        }
        return groupAndSortByDate().getLast().getValue();
    }

    private LocalDate getDayWithMaxAmountRequests() {
        if (groupAndSortByDate().isEmpty()) {
            return null;
        }
        return groupAndSortByDate().getLast().getKey();
    }

    private List<Map.Entry<LocalDate, Long>> groupAndSortByDate() {
        var map = logs.stream().collect(Collectors.groupingBy(log -> log.date().toLocalDate(), Collectors.counting()));
        List<Map.Entry<LocalDate, Long>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        return list;
    }
}
