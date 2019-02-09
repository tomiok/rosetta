package me.tomi.rosetta.string.query;

public final class QueryBuilder {

  public static final String SELECT = "select ";

  public static final String UPDATE = "update ";

  public static final String DELETE = "delete ";

  public static final String INSERT = "insert ";

  public static final String FROM = " from ";

  private static final String WHERE = " where ";

  private static final String LIKE = " like ";

  private static final String PERCENTAGE = "%";

  public static final String ALL = "*";

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

  public QueryBuilder where(final String columnName) {
    sb
        .append(WHERE)
        .append(columnName);
    return this;
  }

  public QueryBuilder like(final String expression) {
    sb
        .append(LIKE)
        .append(String.format("%s%s%s", "%", expression, "%"));

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
