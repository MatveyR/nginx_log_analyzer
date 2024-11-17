package backend.academy.writers;

import backend.academy.logs.LogReport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.AllArgsConstructor;
import static backend.academy.writers.WriterUtils.FORMATTER;

@AllArgsConstructor
public class ADOCWriter implements Writer {
    private LogReport logReport;
    private static final String PATH = "logReport.adoc";
    private static final String TWO_ARGS_LINE = "| %s | %d\n";
    private static final String SEPARATOR = "|===\n";
    private static final String HEADER = "[options=\"header\"]\n";

    @Override
    public void write() {
        WriterUtils.deleteIfExist(PATH);
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH);
             PrintWriter printWriter = new PrintWriter(fileOutputStream)) {
            printWriter.println(getAdocString());
        } catch (IOException e) {
            throw new RuntimeException("Error while writing to the file", e);
        }
    }

    private String getAdocString() {
        return getGeneralInfo()
            + getResources()
            + getCodes()
            + getTypes();
    }

    private String getGeneralInfo() {
        return "==== Общая информация\n\n"
            + HEADER
            + SEPARATOR
            + "| Метрика | Значение\n"
            + String.format(
            "| Начальная дата | %s\n",
            logReport.startDate() == null ? "-" : logReport.startDate().toLocalDate().format(FORMATTER)
        )
            + String.format(
            "| Конечная дата | %s\n",
            logReport.endDate() == null ? "-" : logReport.endDate().toLocalDate().format(FORMATTER)
        )
            + String.format("| Количество запросов | %d\n", logReport.totalAmount())
            + String.format("| Средний размер ответа | %d\n", logReport.averageSize())
            + String.format(
            "| День с наибольшим числом запросов | %s\n",
            logReport.dayWithMaxAmountRequests() == null ? "-"
                : logReport.dayWithMaxAmountRequests().format(FORMATTER)
        )
            + String.format(
            "| Наибольшее число запросов за день | %d\n",
            logReport.maxAmountRequestsPerDay()
        )
            + SEPARATOR;
    }


    private String getResources() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==== Запрашиваемые ресурсы\n\n");
        stringBuilder.append(HEADER);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append("| Ресурс | Количество\n");

        for (var resourceWithAmount : logReport.mostPopularResources()) {
            stringBuilder.append(String.format(
                TWO_ARGS_LINE,
                resourceWithAmount.getKey(),
                resourceWithAmount.getValue()
            ));
        }

        stringBuilder.append(SEPARATOR);
        return stringBuilder.toString();
    }

    private String getCodes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==== Коды ответа\n\n");
        stringBuilder.append(HEADER);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append("| Код | Имя | Количество\n");

        for (var codeWithAmount : logReport.mostPopularAnswers()) {
            stringBuilder.append(String.format(
                "| %d | %s | %d\n",
                codeWithAmount.getKey(),
                WriterUtils.getHttpStatusDescriptions().get(codeWithAmount.getKey()),
                codeWithAmount.getValue()
            ));
        }

        stringBuilder.append(SEPARATOR);
        return stringBuilder.toString();
    }

    private String getTypes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("==== Типы запроса\n\n");
        stringBuilder.append(HEADER);
        stringBuilder.append(SEPARATOR);
        stringBuilder.append("| Тип | Количество\n");

        for (var typeWithAmount : logReport.mostPopularTypes()) {
            stringBuilder.append(String.format(
                TWO_ARGS_LINE,
                typeWithAmount.getKey(),
                typeWithAmount.getValue()
            ));
        }

        stringBuilder.append(SEPARATOR);
        return stringBuilder.toString();
    }
}
