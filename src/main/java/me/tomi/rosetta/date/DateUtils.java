package me.tomi.rosetta.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public final class DateUtils {

  public static LocalDate from(long unixTime) {
    return LocalDate.from(instantHelper(unixTime));
  }

  public static LocalDate from(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDate from(Date date, ZoneId zoneId) {
    return date.toInstant().atZone(zoneId).toLocalDate();
  }

  public Instant from(LocalDate localDate) {
    return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
  }

  public Instant from(LocalDate localDate, ZoneOffset offset) {
    return localDate.atStartOfDay().toInstant(offset);
  }

  public Instant from(LocalDate localDate, ZoneId zoneId) {
    return localDate.atStartOfDay(zoneId).toInstant();
  }

  private static Instant instantHelper(long unixTime) {
    return Instant.ofEpochMilli(unixTime);
  }
}
