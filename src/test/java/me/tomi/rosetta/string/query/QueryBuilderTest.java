package me.tomi.rosetta.string.query;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QueryBuilderTest {

  @Test
  public void create() {
    String s = QueryBuilder
        .get()
        .select()
        .all()
        .from("user")
        .create();
    
    assertTrue(s.equalsIgnoreCase("select * from user"));
  }
}