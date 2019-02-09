package me.tomi.rosetta.string.query;

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
        .where("name")
        .like("tom")
        .create();

    assertEquals("select * from employee where name like %tom%", s);
  }

  @Test
  public void create() {
    String s = QueryBuilder
        .get()
        .select()
        .all()
        .from("user")
        .create();

    assertEquals("select * from user", s);
  }


}