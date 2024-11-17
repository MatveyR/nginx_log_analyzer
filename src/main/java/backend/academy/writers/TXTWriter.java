package backend.academy.writers;

import backend.academy.logs.LogReport;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.AllArgsConstructor;
import static backend.academy.writers.WriterUtils.FORMATTER;

@AllArgsConstructor
public class TXTWriter implements Writer {
    private LogReport logReport;
    private static final String PATH = "logReport.txt";
    private static final String TWO_ARGS_LINE = "%s\t%d\n";

    @Override
    public void write() {
        WriterUtils.deleteIfExist(PATH);
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH);
             PrintWriter printWriter = new PrintWriter(fileOutputStream)) {
            printWriter.println(getTextString());
        } catch (IOException e) {
            throw new RuntimeException("Error while writing to the file", e);
        }
    }

    private String getTextString() {
        return getGeneralInfo()
            + getResources()
            + getCodes()
            + getTypes();
    }

    private String getGeneralInfo() {
        return "Общая информация\n"
            + "Метрика\tЗначение\n"
            + String.format(
            "Начальная дата\t%s\n",
            logReport.startDate() == null ? "-" : logReport.startDate().toLocalDate().format(FORMATTER)
        )
            + String.format(
            "Конечная дата\t%s\n",
            logReport.endDate() == null ? "-" : logReport.endDate().toLocalDate().format(FORMATTER)
        )
            + String.format("Количество запросов\t%d\n", logReport.totalAmount())
            + String.format("Средний размер ответа\t%d\n", logReport.averageSize())
            + String.format(
            "День с наибольшим числом запросов\t%s\n",
            logReport.dayWithMaxAmountRequests() == null ? "-"
                : logReport.dayWithMaxAmountRequests().format(FORMATTER)
        )
            + String.format(
            "Наибольшее число запросов за день\t%d\n",
            logReport.maxAmountRequestsPerDay()
        );
    }


    private String getResources() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nЗапрашиваемые ресурсы\n");
        stringBuilder.append("Ресурс\tКоличество\n");
        for (var resourceWithAmount : logReport.mostPopularResources()) {
            stringBuilder.append(String.format(
                TWO_ARGS_LINE,
                resourceWithAmount.getKey(),
                resourceWithAmount.getValue()
            ));
        }
        return stringBuilder.toString();
    }

    private String getCodes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nКоды ответа\n");
        stringBuilder.append("Код\tИмя\tКоличество\n");
        for (var codeWithAmount : logReport.mostPopularAnswers()) {
            stringBuilder.append(String.format(
                "%d\t%s\t%d\n",
                codeWithAmount.getKey(),
                WriterUtils.getHttpStatusDescriptions().get(codeWithAmount.getKey()),
                codeWithAmount.getValue()
            ));
        }
        return stringBuilder.toString();
    }

    private String getTypes() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nТипы запроса\n");
        stringBuilder.append("Тип\tКоличество\n");
        for (var typeWithAmount : logReport.mostPopularTypes()) {
            stringBuilder.append(String.format(
                TWO_ARGS_LINE,
                typeWithAmount.getKey(),
                typeWithAmount.getValue()
            ));
        }
        return stringBuilder.toString();
    }
}
