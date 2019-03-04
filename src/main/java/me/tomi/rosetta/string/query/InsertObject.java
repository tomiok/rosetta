package me.tomi.rosetta.string.query;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import me.tomi.rosetta.string.StringUtils;

public final class InsertObject {

  private final List<String> columns;

  private final List<String> values;

  private InsertObject() {
    this.columns = Collections.emptyList();
    this.values = Collections.emptyList();
  }

  public InsertObject(final String columnName, final String value) {
    this.columns = Collections.singletonList(columnName);
    this.values = Collections.singletonList(value);
  }

  public InsertObject(List<String> columns, List<String> values) {
    if (columns.size() != values.size()) {
      throw new IllegalArgumentException("columns and values have different number of args: "
                                         + columns.size() + " and " + values.size());
    }
    this.columns = columns;
    this.values = values;
  }

  public static InsertObject of(String column, String value) {
    return new InsertObject(column, value);
  }

  public static InsertObject aggregate(List<InsertObject> insertObjects) {
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
    return values
        .stream()
        .map(value -> {
          if (!StringUtils.isNumeric(value)) {
            return "'" + value + "'";
          }
          return value;
        })
        .collect(toList());
  }
}
