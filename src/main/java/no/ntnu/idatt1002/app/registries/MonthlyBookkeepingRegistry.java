package no.ntnu.idatt1002.app.registries;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;

public class MonthlyBookkeepingRegistry implements Serializable {

    private final Map<YearMonth, MonthlyBookkeeping> bookkeepingMap;

    public MonthlyBookkeepingRegistry() {
        bookkeepingMap = new HashMap<>();
    }

    public void addMonthlyBookkeeping(MonthlyBookkeeping bookkeeping) {
        bookkeepingMap.put(bookkeeping.getYearMonth(), bookkeeping);
    }
    
    public void removeMonthlyBookkeeping(YearMonth yearMonth) {
        bookkeepingMap.remove(yearMonth);
    }
    
    public MonthlyBookkeeping getMonthlyBookkeeping(YearMonth yearMonth) {
        return bookkeepingMap.get(yearMonth);
    }

    public List<Integer> getEmptyYears() {
        return bookkeepingMap.keySet().stream()
            .filter(yearMonth -> bookkeepingMap.get(yearMonth).isEmpty())
            .map(YearMonth::getYear)
            .toList();
    }
    
    public Map<YearMonth, MonthlyBookkeeping> getMonthlyBookkeepingMap() {
        return new HashMap<>(bookkeepingMap);
    }
    
}