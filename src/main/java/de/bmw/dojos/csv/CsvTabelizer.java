package de.bmw.dojos.csv;

import com.codepoetics.protonpack.StreamUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvTabelizer {

    private static final String DELIMITER = ";";

    public Collection<String> toTable(Collection<String> input) {
        if (input.isEmpty())
            return Collections.emptyList();

        int[] columnWidths = detectColumnWidths(input);

        String header = input.iterator().next();
        Stream<String> data = input.stream().skip(1);

        List<String> result = new ArrayList<>();
        result.add(generateDataRow(columnWidths, header));
        result.add(generateDividerRow(columnWidths));
        result.addAll(data.map(s -> generateDataRow(columnWidths, s)).collect(Collectors.toList()));
        return result;
    }

    private static int[] detectColumnWidths(Collection<String> data) {
        int numberOfColumns = data.stream().mapToInt(s -> s.split(DELIMITER).length).max().orElse(0);
        Stream<int[]> lengths = data.stream().map(s -> Arrays.stream(s.split(DELIMITER)).mapToInt(String::length).toArray());
        return lengths.reduce(new int[numberOfColumns], CsvTabelizer::max);
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
                .mapToObj(CsvTabelizer::dashes), "+");

    }

    private static String joinWith(Stream<String> input, String delimiter) {
        return input.collect(Collectors.joining(delimiter)) + delimiter;
    }

    private static String dashes(int count) {
        return new String(new char[count]).replace('\0', '-');
    }

}

