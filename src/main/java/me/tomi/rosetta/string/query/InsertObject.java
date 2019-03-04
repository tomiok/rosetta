package me.tomi.rosetta.string.query;

import java.util.Collections;
import java.util.List;

public class InsertObject {


  private final List<String> columns;

  private final List<String> values;

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

  public List<String> getColumns() {
    return Collections.unmodifiableList(columns);
  }

  public List<String> getValues() {
    return Collections.unmodifiableList(values);
  }
}
