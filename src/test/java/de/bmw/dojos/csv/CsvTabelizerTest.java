package de.bmw.dojos.csv;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class CsvTabelizerTest {
    private CsvTabelizer tabelizer = new CsvTabelizer();

    @Test
    void sanityTest() {
        // given
        List<String> inputLines = Arrays.asList(
                "Name;Strasse;Ort;Alter",
                "Peter Pan;Am Hang 5;12345 Einsam;42",
                "Maria Schmitz;Kölner Straße 45;50123 Köln;43",
                "Paul Meier;Münchener Weg 1;87654 München;65"
        );
        List<String> expected = Arrays.asList(
                "Name         |Strasse         |Ort          |Alter|",
                "-------------+----------------+-------------+-----+",
                "Peter Pan    |Am Hang 5       |12345 Einsam |42   |",
                "Maria Schmitz|Kölner Straße 45|50123 Köln   |43   |",
                "Paul Meier   |Münchener Weg 1 |87654 München|65   |"
        );
        // when
        Collection<String> result = tabelizer.toTable(inputLines);
        // then
        assertLinesMatch(expected, new ArrayList<>(result), "Format of the CSV does not match.");
    }
}
