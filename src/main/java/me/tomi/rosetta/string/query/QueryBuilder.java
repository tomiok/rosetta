package me.tomi.rosetta.string.query;

public class QueryBuilder {

  public static final String SELECT = "select";

  public static final String UPDATE = "update";

  public static final String DELETE = "delete";

  public static final String INSERT = "insert";

  public static final String WHERE = "where";

  private static final String LIKE = "like";

  public static final String ALL = "*";

  private static final StringBuffer sb = new StringBuffer();


  private static class LazyHolder {
    static final QueryBuilder INSTANCE = new QueryBuilder();
  }

  private QueryBuilder() { }

  private static QueryBuilder get() {
    return LazyHolder.INSTANCE;
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
