package me.tomi.rosetta.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public final class DateUtils {

  /**
   * Transform unix epoch time in millis, to a {@link LocalDate}.
   *
   * @param unixTime in millis.
   *
   * @return a {@link LocalDate} object.
   */
  public static LocalDate from(long unixTime) {
    return LocalDate.from(instantHelper(unixTime));
  }

  /**
   * Transform a Date to a {@link LocalDate} using a {@link ZoneId} as the default defined in the system.
   *
   * @param date A {@link Date} object.
   *
   * @return a {@link LocalDate} object.
   */
  public static LocalDate from(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  /**
   * Transform a {@link Date} object with a {@link ZoneId} to a {@link LocalDate}
   *
   * @param date   A {@link Date} object to transform
   * @param zoneId A {@link ZoneId} object to set a zone in the instant object.
   *
   * @return a {@link LocalDate} object.
   */
  public static LocalDate from(Date date, ZoneId zoneId) {
    return date.toInstant().atZone(zoneId).toLocalDate();
  }

  /**
   * Transform a {@link LocalDate} object to an {@link Instant} at
   * the start of a day.
   *
   * @param localDate {@link LocalDate} object to transform.
   *
   * @return an {@link Instant} object.
   */
  public static Instant from(LocalDate localDate) {
    return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
  }

  /**
   * Transform a {@link LocalDate} object with a {@link ZoneOffset} into an {@link Instant} object.
   *
   * @param localDate {@link LocalDate} object to transform.
   * @param offset    {@link ZoneOffset} to modify the instant.
   *
   * @return An {@link Instant} object.
   */
  public static Instant from(LocalDate localDate, ZoneOffset offset) {
    return localDate.atStartOfDay().toInstant(offset);
  }

  /**
   * Transform a {@link LocalDate} object with a {@link ZoneOffset} into an {@link Instant} object.
   *
   * @param localDate {@link LocalDate} object to transform.
   * @param zoneId    {@link ZoneId} to modify the local date.
   *
   * @return An {@link Instant} object.
   */
  public static Instant from(LocalDate localDate, ZoneId zoneId) {
    return localDate.atStartOfDay(zoneId).toInstant();
  }

  private static Instant instantHelper(long unixTime) {
    return Instant.ofEpochMilli(unixTime);
  }
}
