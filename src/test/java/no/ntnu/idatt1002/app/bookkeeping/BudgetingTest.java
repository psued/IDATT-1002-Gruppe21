package no.ntnu.idatt1002.app.bookkeeping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt1002.app.bookkeeping.Budgeting;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

//TODO: Update all tests to function with refactored code
public class BudgetingTest {

    private Budgeting budgeting;

    @BeforeEach
    public void setUp() {
        budgeting = new Budgeting();
    }

    @Test
    public void testAddTransaction() {
        Income income = new Income("test", "test", 100, LocalDate.now());
        budgeting.addTransaction(income);
        assertEquals(1, budgeting.getIncomeList().size());

        Income addedIncome = budgeting.getIncomeList().get(0);
        assertEquals(income, addedIncome);
        
        Expense expense = new Expense("test", "test", 100, LocalDate.now());
        budgeting.addTransaction(expense);
    }

    @Test
    public void testGetExpenseList() {
        Expense expense1 = new Expense("tes1", "test1", 100, LocalDate.now());
        budgeting.addTransaction(expense1);
        assertEquals(1, budgeting.getExpenseList().size());
        
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(expense2);
        assertEquals(2, budgeting.getExpenseList().size());

        Expense addedExpense1 = budgeting.getExpenseList().get(0);
        assertEquals(expense1, addedExpense1);

        Expense addedExpense2 = budgeting.getExpenseList().get(1);
        assertEquals(expense2, addedExpense2);
    }

    @Test
    public void testGetIncomeList() {
        Income expense1 = new Income("test1", "test1", 100, LocalDate.now());
        budgeting.addTransaction(expense1);
        assertEquals(1, budgeting.getIncomeList().size());
        
        Income expense2 = new Income("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(expense2);
        assertEquals(2, budgeting.getIncomeList().size());

        Income addedIncome1 = budgeting.getIncomeList().get(0);
        assertEquals(expense1, addedIncome1);

        Income addedIncome2 = budgeting.getIncomeList().get(1);
        assertEquals(expense2, addedIncome2);
    }

    @Test
    public void testGetTotalExpense() {
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(expense1);
        budgeting.addTransaction(expense2);

        assertEquals(200, budgeting.getTotalExpense());
    }

    @Test
    public void testGetTotalIncome() {
        Income expense1 = new Income("test1", "test1", 100, LocalDate.now());
        Income expense2 = new Income("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(expense1);
        budgeting.addTransaction(expense2);

        assertEquals(200, budgeting.getTotalIncome());
    }
}
