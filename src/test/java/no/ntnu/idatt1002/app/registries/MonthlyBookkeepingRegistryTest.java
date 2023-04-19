package no.ntnu.idatt1002.app.registries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MonthlyBookkeepingRegistryTest {
    
    @Test
    @DisplayName("Test that the accounting object is the same object")
    void testAddAndGetMonthlyBookkeeping() {
        MonthlyBookkeepingRegistry monthlyBookkeepingRegistry = new MonthlyBookkeepingRegistry();
        MonthlyBookkeeping monthlyBookkeeping = new MonthlyBookkeeping(YearMonth.now());
        
        Budgeting budgeting = monthlyBookkeeping.getBudgeting();
        Expense transaction1 = new Expense("Test transaction 1", "Category", 100.0, LocalDate.now());
        Income transaction2 = new Income("Test transaction 1", "Category", 100.0, LocalDate.now());
        budgeting.addExpense(transaction1);
        budgeting.addIncome(transaction2);
        monthlyBookkeepingRegistry.addMonthlyBookkeeping(monthlyBookkeeping);

        MonthlyBookkeeping monthlyBookkeeping2 = new MonthlyBookkeeping(YearMonth.now());
        assertEquals(monthlyBookkeepingRegistry.getMonthlyBookkeepingMap().get(YearMonth.now()),
            monthlyBookkeeping2);
    }
}
