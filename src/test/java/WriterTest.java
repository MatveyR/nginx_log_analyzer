import backend.academy.logs.LogReport;
import backend.academy.writers.MDWriter;
import backend.academy.writers.WriterUtils;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WriterTest {
    @Test
    void testGetMarkDownString() {
        LogReport mockLogReport = createMockLogReport();
        MDWriter writer = new MDWriter(mockLogReport);
        String markdown = writer.getMarkDownString();

        assertTrue(markdown.contains("#### Общая информация"));
        assertTrue(markdown.contains("|        Метрика        |     Значение |"));
        assertTrue(markdown.contains("|  Количество запросов  |       " + mockLogReport.totalAmount() + " |"));
        assertTrue(markdown.contains("|  Средний размер ответа  |       " + mockLogReport.averageSize() + " |"));
        assertTrue(markdown.contains(
            "|  Наибольшее число запросов за день  |       " + mockLogReport.maxAmountRequestsPerDay() + " |"));

        assertTrue(markdown.contains("#### Запрашиваемые ресурсы"));
        assertTrue(markdown.contains("|     Ресурс      | Количество |"));
        for (var entry : mockLogReport.mostPopularResources()) {
            assertTrue(markdown.contains("| " + entry.getKey() + " | " + entry.getValue() + " |"));
        }

        assertTrue(markdown.contains("#### Коды ответа"));
        assertTrue(markdown.contains("| Код |          Имя          | Количество |"));
        for (var entry : mockLogReport.mostPopularAnswers()) {
            assertTrue(markdown.contains(
                "| " + entry.getKey() + " | " + WriterUtils.getHttpStatusDescriptions().get(entry.getKey()) + " | " +
                    entry.getValue() + " |"));
        }

        assertTrue(markdown.contains("#### Типы запроса"));
        assertTrue(markdown.contains("|     Тип      | Количество |"));
        for (var entry : mockLogReport.mostPopularTypes()) {
            assertTrue(markdown.contains("| " + entry.getKey() + " | " + entry.getValue() + " |"));
        }
    }

    private LogReport createMockLogReport() {
        OffsetDateTime startDate = OffsetDateTime.now();
        OffsetDateTime endDate = OffsetDateTime.now();
        int totalAmount = 100;
        int averageSize = 500;
        List<Map.Entry<String, Long>> mostPopularResources =
            new ArrayList<>(Collections.singletonMap("/api/resource", 10L).entrySet());
        List<Map.Entry<Integer, Long>> mostPopularAnswers =
            new ArrayList<>(Collections.singletonMap(200, 20L).entrySet());
        List<Map.Entry<String, Long>> mostPopularTypes =
            new ArrayList<>(Collections.singletonMap("GET", 30L).entrySet());
        LocalDate maxDay = OffsetDateTime.now().toLocalDate();
        int maxRequestsPerDay = 50;

        return new LogReport(
            startDate,
            endDate,
            totalAmount,
            averageSize,
            mostPopularResources,
            mostPopularAnswers,
            mostPopularTypes,
            maxRequestsPerDay,
            maxDay
        );
    }
}
