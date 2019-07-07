package de.lv1871.dojos.csv;

import com.codepoetics.protonpack.StreamUtils;

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

        int[] columnWidths = detectColumnWidths(eingabeZeilen);

        List<String> result = new ArrayList<>();

        result.add(generateDataRow(columnWidths, eingabeZeilen.iterator().next()));
        result.add(generateDividerRow(columnWidths));
        result.addAll(data.map(s -> generateDataRow(columnWidths, s)).collect(Collectors.toList()));

        return result;
    }

    private static int[] detectColumnWidths(Collection<String> data) {
        int columns = data.stream().mapToInt(s -> s.split(DELIMITER).length).max().orElse(0);
        Stream<int[]> lengths = data.stream().map(s -> Arrays.stream(s.split(DELIMITER)).mapToInt(String::length).toArray());
        return lengths.reduce(new int[columns], CsvTabellierer::max);
    }

    private static int[] max(int[] a, int[] b) {
        int[] result = new int[Math.max(a.length, b.length)];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.max(
                    i < a.length ? a[i] : Integer.MIN_VALUE,
                    i < b.length ? b[i] : Integer.MIN_VALUE);
        }
        return result;
    }

    private static String generateDataRow(int[] columnWidths, String row) {
        Stream<String> paddedColumns =
                StreamUtils
                        .zipWithIndex(Arrays.stream(row.split(DELIMITER)))
                        .map(i -> padString(i.getValue(), columnWidths[(int) i.getIndex()]));
        return joinWith(paddedColumns, "|");
    }

    private static String padString(String s, int w) {
        if (s.length() >= w)
            return s;
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < w) sb.append(' ');
        return sb.toString();
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
