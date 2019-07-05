package de.lv1871.dojos.csv;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvTabellierer {

    private static final String DELIMITER = ";";

    public Collection<String> tabelliere(Collection<String> eingabeZeilen) {
        if (eingabeZeilen.isEmpty())
            return Collections.emptyList();

        Stream<String> header = eingabeZeilen.stream().limit(1);
        Stream<String> data = eingabeZeilen.stream().skip(1);

        int[] columnWidths = detectColumnWidths(header.findFirst().get());

        List<String> result = new ArrayList<>();

        result.add(generateDataRow(columnWidths, eingabeZeilen.iterator().next()));
        result.add(generateDividerRow(columnWidths));
        result.addAll(data.map(s -> generateDataRow(columnWidths, s)).collect(Collectors.toList()));

        return result;
    }

    private static int[] detectColumnWidths(String input) {
        return Arrays.stream(input.split(DELIMITER))
                .mapToInt(String::length)
                .toArray();
    }

    private static String generateDataRow(int[] columnWidths, String row) {
        return joinWith(Arrays.stream(row.split(DELIMITER)), "|");
    }

    private static String generateDividerRow(int[] columnWidths) {
        return joinWith(Arrays.stream(columnWidths)
                .mapToObj(CsvTabellierer::dashes), "+");

    }

    private static String joinWith(Stream<String> input, String delimiter) {
        return input.collect(Collectors.joining(delimiter)) + delimiter;
    }

    private static String dashes(int count) {
        return new String(new char[count]).replace('\0', '-');
    }

    public String[] tabelliere(String[] eingabeZeilen) {
        return tabelliere(Arrays.asList(eingabeZeilen)).toArray(new String[0]);
    }
}
