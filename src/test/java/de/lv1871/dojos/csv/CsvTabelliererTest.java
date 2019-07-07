package de.lv1871.dojos.csv;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CsvTabelliererTest {
    private CsvTabellierer tabellierer = new CsvTabellierer();

    @Test
    void sanityTest() {
        // given
        String[] eingabeZeilen = new String[]{
                "Name;Strasse;Ort;Alter",
                "Peter Pan;Am Hang 5;12345 Einsam;42",
                "Maria Schmitz;Kölner Straße 45;50123 Köln;43",
                "Paul Meier;Münchener Weg 1;87654 München;65",
        };
        String[] expected = new String[]{
                "Name         |Strasse         |Ort          |Alter|",
                "-------------+----------------+-------------+-----+",
                "Peter Pan    |Am Hang 5       |12345 Einsam |42   |",
                "Maria Schmitz|Kölner Straße 45|50123 Köln   |43   |",
                "Paul Meier   |Münchener Weg 1 |87654 München|65   |",
        };
        // when
        String[] result = tabellierer.tabelliere(eingabeZeilen);
        // then
        assertArrayEquals(expected, result, "Formatierung ist nicht korrekt.");
    }

    @Test
    void edgeCaseTest() {
        // given
        String[] eingabeZeilen = new String[]{
        };
        String[] expected = new String[]{
        };
        // when
        String[] result = tabellierer.tabelliere(eingabeZeilen);
        // then
        assertArrayEquals(expected, result, "Formatierung ist nicht korrekt.");
    }

    @Test
    void edgeCaseTestWithZeroWidthHeader() {
        // given
        String[] eingabeZeilen = new String[]{
                ";"
        };
        String[] expected = new String[]{
                "|",
                "+"
        };
        // when
        String[] result = tabellierer.tabelliere(eingabeZeilen);
        // then
        assertArrayEquals(expected, result, "Formatierung ist nicht korrekt.");
    }

    @Test
    void easiestCaseTest() {
        // given
        String[] eingabeZeilen = new String[]{
                "X"
        };
        String[] expected = new String[]{
                "X|",
                "-+"
        };
        // when
        String[] result = tabellierer.tabelliere(eingabeZeilen);
        // then
        assertArrayEquals(expected, result, "Formatierung ist nicht korrekt.");
    }

    @Test
    void lengthOfHeaderMatters() {
        // given
        String[] eingabeZeilen = new String[]{
                "Name"
        };
        String[] expected = new String[]{
                "Name|",
                "----+"
        };
        // when
        String[] result = tabellierer.tabelliere(eingabeZeilen);
        // then
        assertArrayEquals(expected, result, "Formatierung ist nicht korrekt.");
    }

    @Test
    void twoColumnsWork() {
        // given
        String[] eingabeZeilen = new String[]{
                "Name;Alter"
        };
        String[] expected = new String[]{
                "Name|Alter|",
                "----+-----+"
        };
        // when
        String[] result = tabellierer.tabelliere(eingabeZeilen);
        // then
        assertArrayEquals(expected, result, "Formatierung ist nicht korrekt.");
    }

    @Test
    void evenEmptyColumnsWork() {
        // given
        String[] eingabeZeilen = new String[]{
                "Name;;Alter"
        };
        String[] expected = new String[]{
                "Name||Alter|",
                "----++-----+"
        };
        // when
        String[] result = tabellierer.tabelliere(eingabeZeilen);
        // then
        assertArrayEquals(expected, result, "Formatierung ist nicht korrekt.");
    }

    @Test
    void threeColumnsWork() {
        // given
        String[] eingabeZeilen = new String[]{
                "Name;Alter;Adresse"
        };
        String[] expected = new String[]{
                "Name|Alter|Adresse|",
                "----+-----+-------+"
        };
        // when
        String[] result = tabellierer.tabelliere(eingabeZeilen);
        // then
        assertArrayEquals(expected, result, "Formatierung ist nicht korrekt.");
    }

    @Test
    void inputWithOneDataRowWorks() {
        // given
        String[] eingabeZeilen = new String[]{
                "Name;Alter;Adresse",
                "Olaf;...45;Somewh."
        };
        String[] expected = new String[]{
                "Name|Alter|Adresse|",
                "----+-----+-------+",
                "Olaf|...45|Somewh.|"
        };
        // when
        String[] result = tabellierer.tabelliere(eingabeZeilen);
        // then
        assertArrayEquals(expected, result, "Formatierung ist nicht korrekt.");
    }
}
