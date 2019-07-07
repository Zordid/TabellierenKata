package de.lv1871.dojos.csv;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class CsvTabelliererFromFileTest {
    private CsvTabellierer tabellierer = new CsvTabellierer();

    @Test
    void sanityTest() throws IOException {
        // given
        Charset utf8 = StandardCharsets.UTF_8;
        List<String> eingabeZeilen = Files
                .readAllLines(Paths.get("src/test/resources/example.csv"), utf8);
        List<String> expected = Files
                .readAllLines(Paths.get("src/test/resources/example-expected.txt"), utf8);
        // when
        List<String> result = new ArrayList<>(tabellierer.tabelliere(eingabeZeilen));
        // then
        assertLinesMatch(expected, result);
    }
}
