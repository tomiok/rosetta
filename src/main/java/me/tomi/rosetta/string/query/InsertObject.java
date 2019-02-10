package me.tomi.rosetta.string.query;

public class InsertObject {

  private final String columnName;

  private final String value;

  public InsertObject(final String columnName, final String value) {
    this.columnName = columnName;
    this.value = value;
  }

  public String getColumnName() {
    return columnName;
  }

  public String getValue() {
    return value;
  }

  public static InsertObject val() {

  }
}
