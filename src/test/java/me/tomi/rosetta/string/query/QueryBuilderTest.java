package me.tomi.rosetta.string.query;

import static me.tomi.rosetta.string.query.Expression.isEqualsTo;
import static me.tomi.rosetta.string.query.Expression.like;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QueryBuilderTest {

  @Test
  public void withLike() {
    String s = QueryBuilder
        .get()
        .select()
        .all()
        .from("employee")
        .where("name", like("tom"))
        .create();

    assertEquals("select * from employee where name like %tom%", s);
  }

  @Test
  public void createSimpleQuery() {
    String s = QueryBuilder
        .get()
        .select()
        .all()
        .from("user")
        .create();

    assertEquals("select * from user", s);
  }

  @Test
  public void testUpdate() {
    String s = QueryBuilder
        .get()
        .update("cart", "price", isEqualsTo(99))
        .where("id", isEqualsTo("5"))
        .create();

    assertEquals("update cart set price = 99 where id = 5", s);
  }
}