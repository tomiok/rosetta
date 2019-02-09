package me.tomi.rosetta.string.query;

import me.tomi.rosetta.string.StringUtils;

public final class QueryBuilder {

  private static final String SELECT = "select ";

  public static final String UPDATE = "update ";

  public static final String DELETE = "delete ";

  public static final String INSERT = "insert ";

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

  public QueryBuilder select(final String... columnNames) {

    String s = String.join(StringUtils.COMMA + StringUtils.SPACE, columnNames);

    sb
        .append(SELECT)
        .append(s);
    return this;
  }

  public QueryBuilder update(final String tableName, final String column, Expression e) {

    sb
        .append(UPDATE)
        .append(tableName)
        .append(SET)
        .append(column)
        .append(e);

    return this;
  }

  public QueryBuilder all() {
    sb
        .append(ALL);
    return this;
  }

  public QueryBuilder from(final String tableName) {
    sb
        .append(FROM)
        .append(tableName);
    return this;
  }

  public QueryBuilder where(final String columnName, final Expression expression) {
    sb
        .append(WHERE)
        .append(columnName)
        .append(expression);
    return this;
  }


  public String create() {
    return sb.toString();
  }


  /*
   *
   *
   * select().all().from("user");
   *
   * select("id", "name").from("user");
   *
   * select().all().from("user").where("id", equals(5)).and("name", like("Tom");
   *
   */
}
