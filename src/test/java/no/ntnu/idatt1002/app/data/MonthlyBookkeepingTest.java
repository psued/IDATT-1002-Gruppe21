package no.ntnu.idatt1002.app.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MonthlyBookkeepingTest {

    private MonthlyBookkeeping monthlyBookkeeping;

    @BeforeEach
    void init() {
        monthlyBookkeeping = new MonthlyBookkeeping(Month.JANUARY);
    }

    @Test
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
    void testGetAccountingNet() {
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

        // Add an expense and an income to the accounting object
        Expense expense = new Expense("Test expense", "Category", 100.0, LocalDate.now());
        Income income = new Income("Test income", "Category", 200.0, LocalDate.now());
        accounting.addExpense(expense);
        accounting.addIncome(income);

        // Assert that the accounting net is the same as the difference between the
        // income and expense
        assertTrue(monthlyBookkeeping.getAccountingNet() == income.getAmount() - expense.getAmount());
    }

    @Test
    void testGetBudgetNet() {

    }

    @Test
    void testGetBudgeting() {

    }

    @Test
    void testGetMonth() {

    }
}
