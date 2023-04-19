package no.ntnu.idatt1002.app.registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.YearMonth;
import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
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

    @Test
    @DisplayName("Test that the accounting object is the same object")
    void testGetAccounting() {
        // Create a MonthlyBookkeeping object

        // Get the accounting object
        Accounting accounting = monthlyBookkeeping.getAccounting();

        // Use a stream to get the expense list and assert that it is empty
        assertEquals(0, accounting.getExpenseList().size());

        // Use a stream to get the income list and assert that it is empty
        assertEquals(0, accounting.getIncomeList().size());

        // Assert that the total expenses is 0
        assertEquals(0, accounting.getTotalExpense());
        assertEquals(0, accounting.getTotalIncome());

        // Add some stuff to the accounting object
        Expense transaction1 = new Expense("Test transaction 1", "Category", 100.0, LocalDate.now());
        Income transaction2 = new Income("Test transaction 1", "Category", 100.0, LocalDate.now());
        accounting.addExpense(transaction1);
        accounting.addIncome(transaction2);

        // Get the accounting object again
        Accounting accounting2 = monthlyBookkeeping.getAccounting();

        // Check that the stuff that was added is there
        assertTrue(accounting2.getExpenseList().contains(transaction1));
        assertTrue(accounting2.getIncomeList().contains(transaction2));
    }

    @Test
    @DisplayName("Test that the budgeting object is the same object")
    void testGetBudgeting() {
        Budgeting budgeting = monthlyBookkeeping.getBudgeting();

        // Use a stream to get the expense list and assert that it is empty
        assertEquals(0, budgeting.getExpenseList().size());

        // Use a stream to get the income list and assert that it is empty
        assertEquals(budgeting.getIncomeList().size(), 0);

        // Assert that the total expenses is 0
        assertEquals(0, budgeting.getTotalExpense());
        assertEquals(0, budgeting.getTotalIncome());

        // Add some stuff to the budgeting object
        Expense transaction1 = new Expense("Test transaction 1", "Category", 100.0, LocalDate.now());
        Income transaction2 = new Income("Test transaction 1", "Category", 100.0, LocalDate.now());
        budgeting.addExpense(transaction1);
        budgeting.addIncome(transaction2);

        // Get the budgeting object again
        Budgeting budgeting2 = monthlyBookkeeping.getBudgeting();

        // Check that the stuff that was added is there
        assertTrue(budgeting2.getExpenseList().contains(transaction1));
        assertTrue(budgeting2.getIncomeList().contains(transaction2));
    }

    @Test
    @DisplayName("Test that the accounting net is correct")
    void testGetAccountingNet() {
        Accounting accounting = monthlyBookkeeping.getAccounting();
        
        assertEquals(0, accounting.getExpenseList().size());
        assertEquals(0, accounting.getIncomeList().size());
        
        assertEquals(0, accounting.getTotalExpense());
        assertEquals(0, accounting.getTotalIncome());

        Expense expense = new Expense("Test expense", "Category", 100.0, LocalDate.now());
        Income income = new Income("Test income", "Category", 200.0, LocalDate.now());
        accounting.addExpense(expense);
        accounting.addIncome(income);
        
        assertEquals(monthlyBookkeeping.getAccountingNet(),
            income.getAmount() - expense.getAmount());
    }

    @Test
    @DisplayName("Test that the budget net is correct")
    void testGetBudgetNet() {
        Budgeting budgeting = monthlyBookkeeping.getBudgeting();
        
        assertEquals(0, budgeting.getExpenseList().size());
        assertEquals(0, budgeting.getIncomeList().size());
        
        assertEquals(0, budgeting.getTotalExpense());
        assertEquals(0, budgeting.getTotalIncome());

        Expense expense = new Expense("Test expense", "Category", 100.0, LocalDate.now());
        Income income = new Income("Test income", "Category", 200.0, LocalDate.now());
        budgeting.addExpense(expense);
        budgeting.addIncome(income);
        
        assertEquals(1, budgeting.getExpenseList().size());
        assertEquals(1, budgeting.getIncomeList().size());
        assertEquals(monthlyBookkeeping.getBudgetNet(), income.getAmount() - expense.getAmount());
    }

    @Test
    @DisplayName("Test that the YearMonth is correct")
    void testGetYearMonth() {
        YearMonth yearMonth = YearMonth.now();
        assertEquals(monthlyBookkeeping.getYearMonth(), yearMonth);
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
}
