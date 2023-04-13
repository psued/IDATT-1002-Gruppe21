package no.ntnu.idatt1002.app.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MonthlyBookkeepingTest {

    private MonthlyBookkeeping monthlyBookkeeping;
    private Calendar calendar = Calendar.getInstance();

    @BeforeEach
    void init() {
        monthlyBookkeeping = new MonthlyBookkeeping(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
    }

    @Test
    @DisplayName("Test that the accounting object is the same object")
    void testGetAccounting() {
        // Create a MonthlyBookkeeping object

        // Get the accounting object
        Accounting accounting = monthlyBookkeeping.getAccounting();

        // Use a stream to get the expense list and assert that it is empty
        assertTrue(accounting.getExpenseList().stream().count() == 0);

        // Use a stream to get the income list and assert that it is empty
        assertTrue(accounting.getIncomeList().stream().count() == 0);

        // Assert that the total expenses is 0
        assertTrue(accounting.getTotalExpense() == 0);
        assertTrue(accounting.getTotalIncome() == 0);

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
        assertTrue(budgeting.getExpenseList().stream().count() == 0);

        // Use a stream to get the income list and assert that it is empty
        assertTrue(budgeting.getIncomeList().stream().count() == 0);

        // Assert that the total expenses is 0
        assertTrue(budgeting.getTotalExpense() == 0);
        assertTrue(budgeting.getTotalIncome() == 0);

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

        assertTrue(accounting.getExpenseList().stream().count() == 0);
        assertTrue(accounting.getIncomeList().stream().count() == 0);

        assertTrue(accounting.getTotalExpense() == 0);
        assertTrue(accounting.getTotalIncome() == 0);

        Expense expense = new Expense("Test expense", "Category", 100.0, LocalDate.now());
        Income income = new Income("Test income", "Category", 200.0, LocalDate.now());
        accounting.addExpense(expense);
        accounting.addIncome(income);

        assertTrue(monthlyBookkeeping.getAccountingNet() == income.getAmount() - expense.getAmount());
    }

    @Test
    @DisplayName("Test that the budget net is correct")
    void testGetBudgetNet() {
        Budgeting budgeting = monthlyBookkeeping.getBudgeting();

        assertTrue(budgeting.getExpenseList().stream().count() == 0);
        assertTrue(budgeting.getIncomeList().stream().count() == 0);

        assertTrue(budgeting.getTotalExpense() == 0);
        assertTrue(budgeting.getTotalIncome() == 0);

        Expense expense = new Expense("Test expense", "Category", 100.0, LocalDate.now());
        Income income = new Income("Test income", "Category", 200.0, LocalDate.now());
        budgeting.addExpense(expense);
        budgeting.addIncome(income);

        assertTrue(budgeting.getExpenseList().stream().count() == 1);
        assertTrue(budgeting.getIncomeList().stream().count() == 1);
        assertTrue(monthlyBookkeeping.getBudgetNet() == income.getAmount() - expense.getAmount());
    }

    @Test
    @DisplayName("Test that the month is correct")
    void testGetMonth() {
        assertEquals(monthlyBookkeeping.getMonth(), calendar.get(Calendar.MONTH));
    }

    @Test
    @DisplayName("Test that the year is correct")
    void testGetYear() {
        assertEquals(monthlyBookkeeping.getYear(), calendar.get(Calendar.YEAR));
    }

    @Test
    @DisplayName("Test equals method")
    void testEquals() {
        MonthlyBookkeeping monthlyBookkeeping2 = new MonthlyBookkeeping(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
        assertTrue(monthlyBookkeeping.equals(monthlyBookkeeping2));
    }
}
