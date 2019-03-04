package me.tomi.rosetta.string.query;

import java.util.List;
import me.tomi.rosetta.string.StringUtils;

public final class QueryBuilder {

  private static final String SELECT = "select ";

  private static final String UPDATE = "update ";

  private static final String DELETE = "delete";

  private static final String INSERT = "insert into ";

  private static final String VALUES = "values";

  private static final String FROM = " from ";

  private static final String WHERE = " where ";

  private static final String ALL = "*";

  private static final String SET = " set ";

  private StringBuffer sb = new StringBuffer();

  private QueryBuilder() { }

  public static QueryBuilder get() {
    return new QueryBuilder();
  }


  public QueryBuilder select() {
    sb
        .append(SELECT);
    return this;
  }

  public QueryBuilder select(String... columnNames) {

    String s = String.join(StringUtils.COMMA + StringUtils.SPACE, columnNames);

    sb
        .append(SELECT)
        .append(s);
    return this;
  }

  public QueryBuilder all() {
    sb
        .append(ALL);
    return this;
  }

  public QueryBuilder update(String tableName, String column, Expression e) {
    sb
        .append(UPDATE)
        .append(tableName)
        .append(SET)
        .append(column)
        .append(e);
    return this;
  }

  public QueryBuilder delete(String tableName) {
    sb
        .append(DELETE)
        .append(FROM)
        .append(tableName);
    return this;
  }

  public QueryBuilder insert(String tableName, InsertObject io) {
    sb
        .append(INSERT)
        .append(tableName)
        .append(StringUtils.SPACE)
        .append(StringUtils.LEFT_P)
        .append(String.join(StringUtils.COMMA, io.getColumns()))
        .append(StringUtils.RIGHT_P)
        .append(StringUtils.SPACE)
        .append(VALUES)
        .append(StringUtils.SPACE)
        .append(StringUtils.LEFT_P)
        .append(String.join(StringUtils.COMMA, io.getFlatValues()))
        .append(StringUtils.RIGHT_P);
    return this;
  }

  public QueryBuilder insert(String tableName, List<InsertObject> ios) {
    sb
        .append(INSERT)
        .append(tableName)
        .append(StringUtils.SPACE)
        .append(StringUtils.LEFT_P)
       // .append(String.join(StringUtils.COMMA, io.getColumns()))
        .append(StringUtils.RIGHT_P)
        .append(StringUtils.SPACE)
        .append(VALUES)
        .append(StringUtils.SPACE)
        .append(StringUtils.LEFT_P)
     //  .append(String.join(StringUtils.COMMA, io.getFlatValues()))
         .append(StringUtils.RIGHT_P);
    return this;
  }

  public QueryBuilder from(String tableName) {
    sb
        .append(FROM)
        .append(tableName);
    return this;
  }

  public QueryBuilder where(String columnName, Expression expression) {
    sb
        .append(WHERE)
        .append(columnName)
        .append(expression);
    return this;
  }

  public String create() {
    return sb.toString();
  }
}
