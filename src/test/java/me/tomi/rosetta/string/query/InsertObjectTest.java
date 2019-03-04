package me.tomi.rosetta.string.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class InsertObjectTest {

  @Test
  public void of() {
    InsertObject io = InsertObject.of("name", "tommy");

    assertEquals(io.getValues().size(), 1);
    assertEquals(io.getColumns().size(), 1);
    assertEquals(io.getValues().get(0), "tommy");
    assertEquals(io.getColumns().get(0), "name");
  }

  @Test
  public void of_withCsvAndArrays() {
    InsertObject io = InsertObject.of("first_name, last_name,  age", "tomas", "norris", "30");
    assertEquals(io.getValues().size(), 3);
    assertEquals(io.getColumns().size(), 3);

    assertEquals(io.getColumns().get(0), "first_name");
    assertEquals(io.getColumns().get(1), "last_name");
    assertEquals(io.getColumns().get(2), "age");
    assertEquals(io.getValues().get(0), "tomas");
    assertEquals(io.getValues().get(1), "norris");
    assertEquals(io.getValues().get(2), "30");
  }

  @Test
  public void of_withLists() {
    InsertObject io = InsertObject.of(
        Arrays.asList("first_name", "last_name", "age"),
        Arrays.asList("tomas", "norris", "30")
    );

    assertEquals(io.getValues().size(), 3);
    assertEquals(io.getColumns().size(), 3);

    assertEquals(io.getColumns().get(0), "first_name");
    assertEquals(io.getColumns().get(1), "last_name");
    assertEquals(io.getColumns().get(2), "age");
    assertEquals(io.getValues().get(0), "tomas");
    assertEquals(io.getValues().get(1), "norris");
    assertEquals(io.getValues().get(2), "30");
  }

  @Test
  public void getFlatValues() {
    InsertObject io = InsertObject.of(
        Arrays.asList("first_name", "last_name", "age"),
        Arrays.asList("tomas", "norris", "30")
    );
    List<String> flatValues = io.getFlatValues();
    assertEquals(flatValues.get(0), "'tomas'");
    assertEquals(flatValues.get(1), "'norris'");
    assertEquals(flatValues.get(2), "30");
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
}