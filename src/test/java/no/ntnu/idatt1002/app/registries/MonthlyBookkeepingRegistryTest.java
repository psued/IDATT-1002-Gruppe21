package no.ntnu.idatt1002.app.registries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.YearMonth;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;
import no.ntnu.idatt1002.app.transactions.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MonthlyBookkeepingRegistryTest {
  
  MonthlyBookkeepingRegistry monthlyBookkeepingRegistry;
  YearMonth yearMonth;
  
  @BeforeEach
  void init() {
    monthlyBookkeepingRegistry = new MonthlyBookkeepingRegistry();
    monthlyBookkeepingRegistry.putMonthlyBookkeeping(new MonthlyBookkeeping(YearMonth.now()));
    yearMonth = YearMonth.now();
  }
  
  @Test
  @DisplayName("Test the getMonthlyBookkeeping method")
  void testGetMonthlyBookkeeping() {
    assertTrue(monthlyBookkeepingRegistry.getMonthlyBookkeeping(yearMonth) != null);
  }
  
  @Test
  @DisplayName("Test the getMonthlyBookkeepingMap method")
  void testGetMonthlyBookkeepingMap() {
    assertEquals(1, monthlyBookkeepingRegistry.getMonthlyBookkeepingMap().size());
  }
  
  @Test
  @DisplayName("Test the addMonthlyBookkeeping method")
  void testAddMonthlyBookkeeping() {
    monthlyBookkeepingRegistry.putMonthlyBookkeeping(new MonthlyBookkeeping(YearMonth.now().plusMonths(1)));
    assertEquals(2, monthlyBookkeepingRegistry.getMonthlyBookkeepingMap().size());
  }
  
  @Test
  @DisplayName("Test the removeMonthlyBookkeeping method")
  void testRemoveMonthlyBookkeeping() {
    monthlyBookkeepingRegistry.removeMonthlyBookkeeping(yearMonth);
    assertEquals(0, monthlyBookkeepingRegistry.getMonthlyBookkeepingMap().size());
  }
  
  @Test
  @DisplayName("Test the getEmptyYears method")
  void testGetEmptyYears() {
    assertTrue(monthlyBookkeepingRegistry.isYearEmpty(yearMonth));
    monthlyBookkeepingRegistry.getMonthlyBookkeeping(yearMonth).getBookkeeping(true, true)
        .addTransaction(new Expense("Test", "Test", 100, null));
    assertFalse(monthlyBookkeepingRegistry.isYearEmpty(yearMonth));
  }
}
