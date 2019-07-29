#Function Kata “CSV Table-izer”
Write a function to create a table from CSV data.

```
Collection<String> Tabelizer.toTable(Collection<String> lines)
```
The input to the function is a collection of strings. Each string is formatted as a CSV record. Here’s an example for a possible input:

```
Name;Street;City;Age
Peter Pan;Am Hang 5;12345 Einsam;42
Maria Schmitz;Kölner Straße 45;50123 Köln;43
Paul Meier;Münchener Weg 1;87654 München;65
```
In the input a semicolon separates values within a line. More complicated features of CSV (e.g. delimiters within a value) need not be implemented. The input always is correctly formatted; no validation necessary.

The output should be the data formatted as an “ASCII table”. The first record is interpreted as a header line. A separator line should follow the header line. The column width follows the longest value in a column (which includes the header). Here’s an example output for the above input:
```
Name         |Street          |City         |Age|
-------------+----------------+-------------+---+
Peter Pan    |Am Hang 5       |12345 Einsam |42 |
Maria Schmitz|Kölner Straße 45|50123 Köln   |43 |
Paul Meier   |Münchener Weg 1 |87654 München|65 |
```

[Taken from CCD School](https://ccd-school.de/coding-dojo/function-katas/csv-tabellieren/)

## Extra tasks

### Parsen von anderen Dateien
Try to parse the given `src/test/resources/sample.csv` file. Is your implementation ready for this?

### Page breaks
Enhance your algorithm to also take an `int` parameter that indicates the maximum amount of rows per page. The page is full if this amount is reached. In that case, output an empty line to indicate a page break and on the "next page" following the blank line, start again with the header and go on with the data.

Example:

Given this input:
```
Name;Strasse;Ort;Alter
Peter Pan;Am Hang 5;12345 Einsam;42
Maria Schmitz;Kölner Straße 45;50123 Köln;43
Paul Meier;Münchener Weg 1;87654 München;65
```
and **4** lines per page, the result should look like this:

```
Name         |Strasse         |Ort          |Alter|
-------------+----------------+-------------+-----+
Peter Pan    |Am Hang 5       |12345 Einsam |42   |
Maria Schmitz|Kölner Straße 45|50123 Köln   |43   |

Name         |Strasse         |Ort          |Alter|
-------------+----------------+-------------+-----+
Paul Meier   |Münchener Weg 1 |87654 München|65   |
```