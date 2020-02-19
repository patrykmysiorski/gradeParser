import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Dziekanat {
    public static void main(String[] args) throws IOException {
        File file = new File("dane.txt");
        var scanner = new Scanner(file);
        var dziekanatRecords = loadFile(scanner);
        scanner.close();
        printRecords(dziekanatRecords);
        FileWriter writer = new FileWriter("output.txt");
        for (String str : dziekanatRecords) {
            writer.write(str + System.lineSeparator());
        }
        writer.close();
    }

    private static List<String> loadFile(Scanner scanner) {
        var records = "";
        final var DATE_PATTERN = "\\d\\d\\.\\d\\d\\.\\d\\d";
        final var ID_PATTERN = "[0-9]{8}";
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            records = line;
        }
        List<String> rows = new ArrayList<>(Arrays.asList(records.split("<tr")));
        rows.remove(0);
        List<String> przedmioty = new ArrayList<>();
        for (var record : rows) {
            przedmioty.add(record.replace("<td>", "")
                    .replace("</td>", "$")
                    .replace("&nbsp;", "brak")
                    .replace("class=\"gridDane\">", "")
                    .replace("class=\"gridDane\" style=\"background-color:#FFFF00;\">", "")
                    .replace("<td style=\"color:#000000;\">", "")
                    .replace("</tr>", "")
                    .replace("<span class=\"ocena\">", "")
                    .replace("</span>", "")
                    .replaceAll(DATE_PATTERN, "date")
                    .replace("<br>date", "")
                    .replace("</tbody>", "")
                    .replaceAll(ID_PATTERN, "ID_TO_DELETE")
                    .replace("$brak$brak$brak", "")
                    .trim()
            );
        }
        przedmioty.remove(0);
        return przedmioty;
    }

    private static void printRecords(List<String> dziekanatRecords) {
        for (var i = 0; i < 10; i++) {
            System.out.print(i + ". ");
            System.out.println(dziekanatRecords.get(i));
        }
    }
}