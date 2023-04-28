package no.ntnu.idatt1002.app.registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.YearMonth;
import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MonthlyBookkeepingTest {
  
  private MonthlyBookkeeping monthlyBookkeeping;
  
  @BeforeEach
  void init() {
    monthlyBookkeeping = new MonthlyBookkeeping(YearMonth.now());
  }
  
  @Nested
  @DisplayName("Test getBookkeeping method")
  class TestGetBookkeeping {
    @Test
    @DisplayName("Test that the getBookkeeping method returns the correct instance")
    void testGetBookkeeping() {
      assertTrue(monthlyBookkeeping.getBookkeeping(true, true) instanceof Accounting);
      assertTrue(monthlyBookkeeping.getBookkeeping(true, false) instanceof Accounting);
      assertTrue(monthlyBookkeeping.getBookkeeping(false, true) instanceof Budgeting);
      assertTrue(monthlyBookkeeping.getBookkeeping(false, false) instanceof Budgeting);
    }
    
    @Test
    @DisplayName("Test that the getBookkeeping method returns the correct object")
    void testGetBookkeepingCorrectObject() {
      Income income = new Income("Description", "Category", 100, null);
      Expense expense = new Expense("Description", "Category", 100, null);
      
      monthlyBookkeeping.getBookkeeping(true, true).addTransaction(income);
      monthlyBookkeeping.getBookkeeping(false, true).addTransaction(expense);
      
      // Test that getBookkeeping returns different objects for different parameters
      assertFalse(monthlyBookkeeping.getBookkeeping(true, true) ==
          monthlyBookkeeping.getBookkeeping(true, false));
      assertFalse(monthlyBookkeeping.getBookkeeping(false, true) ==
          monthlyBookkeeping.getBookkeeping(false, false));
      
      // Test that getBookkeeping returns correct object and that they are updated
      assertEquals(monthlyBookkeeping.getBookkeeping(true, true).getTransactions().get(0), income);
      assertEquals(monthlyBookkeeping.getBookkeeping(false, true).getTransactions().get(0), expense);
      
      // Test that the other objects are not unexpectedly updated
      assertEquals(monthlyBookkeeping.getBookkeeping(true, false).getTransactions().size(), 0);
      assertEquals(monthlyBookkeeping.getBookkeeping(false, false).getTransactions().size(), 0);
    }
  }
  
  @Nested
  @DisplayName("Test getTotalBookkeeping method")
  class TestGetTotalBookkeeping {
    @Test
    @DisplayName("Test that the getTotalBookkeeping method returns the correct instance")
    void testGetTotalBookkeeping() {
      assertTrue(monthlyBookkeeping.getTotalBookkeeping(true) instanceof Accounting);
      assertTrue(monthlyBookkeeping.getTotalBookkeeping(false) instanceof Budgeting);
    }
    
    @Test
    @DisplayName("Test that the getTotalBookkeeping method returns the correct object")
    void testGetTotalBookkeepingCorrectObject() {
      Income income = new Income("Description", "Category", 100, null);
      Expense expense = new Expense("Description", "Category", 100, null);
      
      
      
      monthlyBookkeeping.getBookkeeping(true, true).addTransaction(income);
      monthlyBookkeeping.getBookkeeping(false, true).addTransaction(expense);
      
      // Test that getTotalBookkeeping returns correct object and that they are updated
      assertTrue(monthlyBookkeeping.getTotalBookkeeping(true).getTransactions().get(0).equals(income));
      assertEquals(monthlyBookkeeping.getTotalBookkeeping(true).getTransactions().get(0), income);
      assertEquals(monthlyBookkeeping.getTotalBookkeeping(false).getTransactions().get(0), expense);
      
      // Test that the other objects are not unexpectedly updated
      assertEquals(monthlyBookkeeping.getTotalBookkeeping(true).getTransactions().size(), 1);
      assertEquals(monthlyBookkeeping.getTotalBookkeeping(false).getTransactions().size(), 1);
    }
  }
  
  @Test
  @DisplayName("Test the getYearMonth method")
  void testGetYearMonth() {
    assertEquals(monthlyBookkeeping.getYearMonth(), YearMonth.now());
  }
  
  @Nested
  @DisplayName("Test equals method")
  class TestEquals {
    @Test
    @DisplayName("Test that the equals method returns true when comparing the same object")
    void testEqualsSameObject() {
      assertEquals(monthlyBookkeeping, monthlyBookkeeping);
    }
    
    @Test
    @DisplayName("Test that the equals method returns false when comparing to null")
    void testEqualsNull() {
      assertNotEquals(null, monthlyBookkeeping);
    }
    
    @Test
    @DisplayName("Test that the equals method returns false when comparing to a different class")
    void testEqualsDifferentClass() {
      assertNotEquals(monthlyBookkeeping, LocalDate.now());
    }
    
    @Test
    @DisplayName("Test that the equals method returns false when the YearMonth is different")
    void testEqualsDifferentObject() {
      MonthlyBookkeeping monthlyBookkeeping2 =
          new MonthlyBookkeeping(YearMonth.now().plusMonths(1));
      assertNotEquals(monthlyBookkeeping, monthlyBookkeeping2);
    }
  }

  @Test
  @DisplayName("Test Is Empty")
  void testIsEmpty() {
    assertTrue(monthlyBookkeeping.isEmpty());
    monthlyBookkeeping.getBookkeeping(true, true).addTransaction(new Income("Description", "Category", 100, null));
    assertFalse(monthlyBookkeeping.isEmpty());
  }
}
