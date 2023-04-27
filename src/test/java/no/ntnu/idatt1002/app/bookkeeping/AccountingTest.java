package no.ntnu.idatt1002.app.bookkeeping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

public class AccountingTest {

    private Accounting accounting;

    @BeforeEach
    public void setUp() {
        accounting = new Accounting();
    }

    @Test
    public void testGetExpenseList() {
        Expense expense1 = new Expense("tes1", "test1", 100, LocalDate.now());
        accounting.addTransaction(expense1);
        assertEquals(1, accounting.getExpenseList().size());
        
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(expense2);
        assertEquals(2, accounting.getExpenseList().size());

        Expense addedExpense1 = accounting.getExpenseList().get(0);
        assertEquals(expense1, addedExpense1);

        Expense addedExpense2 = accounting.getExpenseList().get(1);
        assertEquals(expense2, addedExpense2);
    }

    @Test
    public void testGetIncomeList() {
        Income expense1 = new Income("test1", "test1", 100, LocalDate.now());
        accounting.addTransaction(expense1);
        assertEquals(1, accounting.getIncomeList().size());
        
        Income expense2 = new Income("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(expense2);
        assertEquals(2, accounting.getIncomeList().size());

        Income addedIncome1 = accounting.getIncomeList().get(0);
        assertEquals(expense1, addedIncome1);

        Income addedIncome2 = accounting.getIncomeList().get(1);
        assertEquals(expense2, addedIncome2);
    }

    @Test

    public void testGetTotalExpense() {
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(expense1);
        accounting.addTransaction(expense2);

        assertEquals(200, accounting.getTotalExpense());
    }

    @Test
    public void testGetTotalIncome() {
        Income expense1 = new Income("test1", "test1", 100, LocalDate.now());
        Income expense2 = new Income("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(expense1);
        accounting.addTransaction(expense2);

        assertEquals(200, accounting.getTotalIncome());
    }

}
