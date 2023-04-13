package no.ntnu.idatt1002.app.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MonthlyBookkeepingRegistryTest {

    private Calendar calendar = Calendar.getInstance();

    @Test
    @DisplayName("Test that the accounting object is the same object")
    void testAddAndGetMonthlyBookkeeping() {
        MonthlyBookkeepingRegistry monthlyBookkeepingRegistry = new MonthlyBookkeepingRegistry();
        MonthlyBookkeeping monthlyBookkeeping = new MonthlyBookkeeping(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        
        Budgeting budgeting = monthlyBookkeeping.getBudgeting();
        Expense transaction1 = new Expense("Test transaction 1", "Category", 100.0, LocalDate.now());
        Income transaction2 = new Income("Test transaction 1", "Category", 100.0, LocalDate.now());
        budgeting.addExpense(transaction1);
        budgeting.addIncome(transaction2);
        monthlyBookkeepingRegistry.addMonthlyBookkeeping(monthlyBookkeeping);

        MonthlyBookkeeping monthlyBookkeeping2 = new MonthlyBookkeeping(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        assertEquals(monthlyBookkeepingRegistry.getMonthlyBookkeepingList().get(0), monthlyBookkeeping2);
    }
}
