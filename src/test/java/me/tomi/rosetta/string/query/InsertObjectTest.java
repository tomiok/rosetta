package me.tomi.rosetta.string.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class InsertObjectTest {

  @Test
  public void of() {
  }

  @Test
  public void getColumns() {
  }

  @Test
  public void getValues() {
  }

  @Test
  public void getFlatValues() {
  }

  @Test
  public void getCsvColumns() {
    List<InsertObject> objects = Arrays.asList(new InsertObject("first_name", "tommy"),
        new InsertObject("last_name", "norris"), new InsertObject("nationality", "Argentinian"));
    InsertObject io = InsertObject.aggregate(objects);

    assertEquals(io.getColumns().size(), 3);
    assertEquals(io.getValues().size(), 3);
    assertTrue(io.getColumns().contains("last_name"));
    assertTrue(io.getColumns().contains("first_name"));
    assertTrue(io.getColumns().contains("nationality"));

    assertTrue(io.getValues().contains("tommy"));
    assertTrue(io.getValues().contains("norris"));
    assertTrue(io.getValues().contains("Argentinian"));
  }

  @Test
  public void getCsvValues() {
  }
}