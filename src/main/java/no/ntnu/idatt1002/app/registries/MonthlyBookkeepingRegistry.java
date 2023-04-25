package no.ntnu.idatt1002.app.registries;

import java.io.Serializable;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;

public class MonthlyBookkeepingRegistry implements Serializable {
  
  private final Map<YearMonth, MonthlyBookkeeping> monthMonthlyBookkeepingMap;
  
  public MonthlyBookkeepingRegistry() {
    monthMonthlyBookkeepingMap = new HashMap<>();
  }
  
  public void addMonthlyBookkeeping(MonthlyBookkeeping bookkeeping) {
    monthMonthlyBookkeepingMap.put(bookkeeping.getYearMonth(), bookkeeping);
  }
  
  public void updateMonthlyBookkeeping(MonthlyBookkeeping bookkeeping) {
    monthMonthlyBookkeepingMap.put(bookkeeping.getYearMonth(), bookkeeping);
  }
  
  public void removeMonthlyBookkeeping(YearMonth yearMonth) {
    monthMonthlyBookkeepingMap.remove(yearMonth);
  }
  
  public MonthlyBookkeeping getMonthlyBookkeeping(YearMonth yearMonth) {
    return monthMonthlyBookkeepingMap.get(yearMonth);
  }
  
  //Checks all months in the bookkeepingMap and returns a list of years that only have empty months
  public boolean isYearEmpty(YearMonth yearMonth) {
    List<Month> existingMonths =
        monthMonthlyBookkeepingMap.keySet().stream().filter(month -> month.getYear() == month.getYear()).map(YearMonth::getMonth).toList();
    
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
  
  public Map<YearMonth, MonthlyBookkeeping> getMonthlyBookkeepingMap() {
    return new HashMap<>(monthMonthlyBookkeepingMap);
  }
  
}