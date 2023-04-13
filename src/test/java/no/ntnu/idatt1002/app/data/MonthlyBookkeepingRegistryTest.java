package no.ntnu.idatt1002.app.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MonthlyBookkeepingRegistryTest {

    @Test
    @DisplayName("Test that the accounting object is the same object")
    void testAddAndGetMonthlyBookkeeping() {
        MonthlyBookkeepingRegistry monthlyBookkeepingRegistry = new MonthlyBookkeepingRegistry();
        MonthlyBookkeeping monthlyBookkeeping = new MonthlyBookkeeping(Month.JANUARY);
        
        Budgeting budgeting = monthlyBookkeeping.getBudgeting();
        
    }

    @Test
    void testGetMonthlyBookkeepingList() {

    }
}
