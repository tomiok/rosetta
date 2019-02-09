package me.tomi.rosetta.string.query;

import me.tomi.rosetta.string.StringUtils;

public final class QueryBuilder {

  public static final String SELECT = "select";

  public static final String UPDATE = "update";

  public static final String DELETE = "delete";

  public static final String INSERT = "insert";

  public static final String FROM = "from";

  public static final String WHERE = "where";

  private static final String LIKE = "like";

  private static final String PERCENTAGE = "%";

  public static final String ALL = "*";

  private static final StringBuffer sb = new StringBuffer();


  private static class LazyHolder {
    static final QueryBuilder INSTANCE = new QueryBuilder();
  }

  private QueryBuilder() { }

  public static QueryBuilder get() {
    return LazyHolder.INSTANCE;
  }


  public QueryBuilder select() {
    sb
        .append(SELECT)
        .append(StringUtils.SPACE);
    return this;
  }

  public QueryBuilder all() {
    sb
        .append(ALL)
        .append(StringUtils.SPACE);
    return this;
  }

  public QueryBuilder from(final String tableName) {
    sb
        .append(FROM)
        .append(StringUtils.SPACE)
        .append(tableName);
    return this;
  }

  public QueryBuilder like(final String expression) {
    sb
        .append(LIKE)
        .append(String.format("% %s %", expression));

    return this;† 
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
