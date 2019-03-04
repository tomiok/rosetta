package me.tomi.rosetta.string.query;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import me.tomi.rosetta.annotations.NotNull;
import me.tomi.rosetta.string.StringUtils;

public final class InsertObject {

  private final List<String> columns;

  private final List<String> values;

  public InsertObject(final String columnName, final String value) {
    if (columnName.contains(StringUtils.COMMA)) {
      throw new IllegalArgumentException("Commas are not allowed to columns in ANSI SQL - If you are trying a "
                                         + "multi insert, please use Lists!");
    }
    this.columns = Collections.singletonList(columnName.trim());
    this.values = Collections.singletonList(value.trim());
  }

  public InsertObject(List<String> columns, List<String> values) {
    if (columns.size() != values.size()) {
      throw new IllegalArgumentException("columns and values have different number of args: "
                                         + columns.size() + " and " + values.size());
    }
    this.columns = columns.stream().map(String::trim).collect(toList());
    this.values = values.stream().map(String::trim).collect(toList());
  }

  public static InsertObject of(String column, String value) {
    return new InsertObject(column, value);
  }

  public static InsertObject of(List<String> columns, List<String> values) {
    return new InsertObject(columns, values);
  }

  public static InsertObject of(String csvColumns, List<String> values) {
    List<String> columns = Arrays.asList(csvColumns.trim().split(StringUtils.COMMA));
    return new InsertObject(columns, values);
  }

  public static InsertObject of(String csvColumns, String... arrValues) {
    List<String> columns = Arrays.asList(StringUtils.replaceSpaces(csvColumns).split(StringUtils.COMMA));
    List<String> values = Arrays.asList(arrValues);
    return new InsertObject(columns, values);
  }

  public static InsertObject aggregate(@NotNull List<InsertObject> insertObjects) {
    return new InsertObject(
        insertObjects
            .stream()
            .flatMap(insObjs -> insObjs.getColumns().stream())
            .collect(toList()),
        insertObjects
            .stream()
            .flatMap(insObjs -> insObjs.getValues().stream())
            .collect(toList())
    );
  }

  public List<String> getColumns() {
    return Collections.unmodifiableList(columns);
  }

  public List<String> getValues() {
    return Collections.unmodifiableList(values);
  }

  public List<String> getFlatValues() {
    return Collections.unmodifiableList(values
        .stream()
        .map(value -> {
          if (!StringUtils.isNumeric(value)) {
            return "'" + value + "'";
          }
          return value;
        })
        .collect(toList()));
  }
}
