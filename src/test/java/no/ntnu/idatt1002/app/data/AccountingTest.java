package no.ntnu.idatt1002.app.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountingTest {

    private Accounting accounting;

    @BeforeEach
    public void setUp() {
        accounting = new Accounting();
    }

    @Test
    public void testAddExpense() {
        Expense expense = new Expense("test", "test", 100, LocalDate.now());
        accounting.addExpense(expense);
        assertEquals(1, accounting.getExpenseList().size());

        Expense addedExpense = accounting.getExpenseList().get(0);
        assertEqualTransaction(expense, addedExpense);
    }

    @Test
    public void testAddIncome() {
        Income income = new Income("test", "test", 100, LocalDate.now());
        accounting.addIncome(income);
        assertEquals(1, accounting.getIncomeList().size());

        Income addedIncome = accounting.getIncomeList().get(0);
        assertEqualTransaction(income, addedIncome);
    }

    @Test
    public void testGetExpenseList() {
        Expense expense1 = new Expense("tes1", "test1", 100, LocalDate.now());
        accounting.addExpense(expense1);
        assertEquals(1, accounting.getExpenseList().size());
        
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        accounting.addExpense(expense2);
        assertEquals(2, accounting.getExpenseList().size());

        Expense addedExpense1 = accounting.getExpenseList().get(0);
        assertEqualTransaction(expense1, addedExpense1);

        Expense addedExpense2 = accounting.getExpenseList().get(1);
        assertEqualTransaction(expense2, addedExpense2);
    }

    @Test
    public void testGetIncomeList() {
        Income expense1 = new Income("test1", "test1", 100, LocalDate.now());
        accounting.addIncome(expense1);
        assertEquals(1, accounting.getIncomeList().size());
        
        Income expense2 = new Income("test2", "test2", 100, LocalDate.now());
        accounting.addIncome(expense2);
        assertEquals(2, accounting.getIncomeList().size());

        Income addedIncome1 = accounting.getIncomeList().get(0);
        assertEqualTransaction(expense1, addedIncome1);

        Income addedIncome2 = accounting.getIncomeList().get(1);
        assertEqualTransaction(expense2, addedIncome2);
    }

    @Test
    public void testGetTotalExpense() {
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        accounting.addExpense(expense1);
        accounting.addExpense(expense2);

        assertEquals(200, accounting.getTotalExpense());
    }

    @Test
    public void testGetTotalIncome() {
        Income expense1 = new Income("test1", "test1", 100, LocalDate.now());
        Income expense2 = new Income("test2", "test2", 100, LocalDate.now());
        accounting.addIncome(expense1);
        accounting.addIncome(expense2);

        assertEquals(200, accounting.getTotalIncome());
    }

    private void assertEqualTransaction(Transaction transaction1, Transaction transaction2) {
        assertEquals(transaction1.getAmount(), transaction2.getAmount());
        assertEquals(transaction1.getCategory(), transaction2.getCategory());
        assertEquals(transaction1.getDate(), transaction2.getDate());
        assertEquals(transaction1.getDescription(), transaction2.getDescription());
    }
}
