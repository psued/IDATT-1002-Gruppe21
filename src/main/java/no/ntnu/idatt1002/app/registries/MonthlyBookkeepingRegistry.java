package no.ntnu.idatt1002.app.registries;

import java.io.Serializable;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;

/**
 * The MonthlyBookkeepingRegistry class represents a registry for all the monthly bookkeeping
 * objects in the application. Each monthly bookkeeping object is identified by a {@link YearMonth}.
 *
 * <p>It implements the Serializable interface for serialization and deserialization of object.
 *
 * @see YearMonth
 * @see MonthlyBookkeeping
 */
public class MonthlyBookkeepingRegistry implements Serializable {
  
  //Map with YearMonth as key and MonthlyBookkeeping as value
  private final Map<YearMonth, MonthlyBookkeeping> monthMonthlyBookkeepingMap;
  
  /**
   * Creates a MonthlyBookkeepingRegistry object with an empty map.
   */
  public MonthlyBookkeepingRegistry() {
    monthMonthlyBookkeepingMap = new HashMap<>();
  }
  
  /**
   * Put a monthly bookkeeping object into the registry with the monthly bookkeeping's
   * {@link YearMonth} as key. This will either overwrite the existing monthly bookkeeping object
   * or add a new one.
   *
   * @param bookkeeping the monthly bookkeeping object to add / update
   * @throws IllegalArgumentException if bookkeeping is null
   */
  public void putMonthlyBookkeeping(MonthlyBookkeeping bookkeeping)
      throws IllegalArgumentException {
    if (bookkeeping == null) {
      throw new IllegalArgumentException("bookkeeping cannot be null");
    }
    monthMonthlyBookkeepingMap.put(bookkeeping.getYearMonth(), bookkeeping);
  }
  
  /**
   * Remove a monthly bookkeeping object from the registry with the monthly bookkeeping's
   * {@link YearMonth} as key.
   *
   * @param yearMonth the year and month of the monthly bookkeeping object to remove
   * @throws IllegalArgumentException if yearMonth is null
   */
  public void removeMonthlyBookkeeping(YearMonth yearMonth) throws IllegalArgumentException {
    if (yearMonth == null) {
      throw new IllegalArgumentException("yearMonth cannot be null");
    }
    monthMonthlyBookkeepingMap.remove(yearMonth);
  }
  
  /**
   * Get a monthly bookkeeping object from the registry with the monthly bookkeeping's
   * {@link YearMonth} as key.
   *
   * @param yearMonth the year and month of the monthly bookkeeping object to get
   * @return the monthly bookkeeping object with the given year and month
   * @throws IllegalArgumentException if yearMonth is null
   */
  public MonthlyBookkeeping getMonthlyBookkeeping(YearMonth yearMonth)
      throws IllegalArgumentException {
    if (yearMonth == null) {
      throw new IllegalArgumentException("yearMonth cannot be null");
    }
    return monthMonthlyBookkeepingMap.get(yearMonth);
  }
  
  /**
   * Check if the registry contains a monthly bookkeeping object with the given {@link YearMonth}
   * that has any transactions.
   *
   * @param yearMonth the year and month of the monthly bookkeeping object to check for
   * @return true if the registry contains a monthly bookkeeping object with the given year and
   *         month, false otherwise
   * @throws IllegalArgumentException if yearMonth is null
   */
  public boolean isYearEmpty(YearMonth yearMonth) throws IllegalArgumentException {
    if (yearMonth == null) {
      throw new IllegalArgumentException("yearMonth cannot be null");
    }
    
    List<Month> existingMonths = monthMonthlyBookkeepingMap.keySet().stream()
        .filter(month -> month.getYear() == month.getYear()).map(YearMonth::getMonth).toList();
    
    for (Month month : existingMonths) {
      if (monthMonthlyBookkeepingMap.get(yearMonth.withMonth(month.getValue())) == null) {
        continue;
      }
      if (!monthMonthlyBookkeepingMap.get(yearMonth.withMonth(month.getValue())).isEmpty()) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Get a map of all the monthly bookkeeping objects in the registry.
   *
   * @return a map of all the monthly bookkeeping objects in the registry
   */
  public Map<YearMonth, MonthlyBookkeeping> getMonthlyBookkeepingMap() {
    return new HashMap<>(monthMonthlyBookkeepingMap);
  }
  
}