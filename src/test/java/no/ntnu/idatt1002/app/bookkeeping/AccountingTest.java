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
    @DisplayName("Test Get Expense List")
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
    @DisplayName("Test Get Income List")
    public void testGetIncomeList() {
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        accounting.addTransaction(income1);
        assertEquals(1, accounting.getIncomeList().size());
        
        Income income2 = new Income("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(income2);
        assertEquals(2, accounting.getIncomeList().size());

        Income addedIncome1 = accounting.getIncomeList().get(0);
        assertEquals(income1, addedIncome1);

        Income addedIncome2 = accounting.getIncomeList().get(1);
        assertEquals(income2, addedIncome2);
    }

    @Test
    @DisplayName("Test Get Total Expense")
    public void testGetTotalExpense() {
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(expense1);
        accounting.addTransaction(expense2);

        assertEquals(200, accounting.getTotalExpense());
    }

    @Test
    @DisplayName("Test Get Total Income")
    public void testGetTotalIncome() {
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        Income income2 = new Income("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(income1);
        accounting.addTransaction(income2);

        assertEquals(200, accounting.getTotalIncome());
    }

    @Test
    @DisplayName("Test Remove Transaction")
    public void testRemoveExpense() {
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        accounting.addTransaction(expense1);
        assertEquals(1, accounting.getExpenseList().size());
        accounting.removeTransaction(expense1);
        assertEquals(0, accounting.getExpenseList().size());
    }

    @Test
    @DisplayName("Test Update Transaction")
    public void testUpdateExpense(){
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        Income income2 = new Income("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(expense1);
        accounting.addTransaction(income1);
        accounting.updateTransaction(income1, expense2);
        assertEquals(expense2, accounting.getTransactions().get(1));
        accounting.updateTransaction(expense1, income2);
        assertEquals(income2, accounting.getTransactions().get(0));


    }

    @Test
    @DisplayName("Test Get Income List")
    public void getIncomeList(){
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        accounting.addTransaction(income1);
        assertEquals(1, accounting.getIncomeList().size());

        Income income2 = new Income("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(income2);
        assertEquals(2, accounting.getIncomeList().size());

        Income addedIncome1 = accounting.getIncomeList().get(0);
        assertEquals(income1, addedIncome1);

        Income addedIncome2 = accounting.getIncomeList().get(1);
        assertEquals(income2, addedIncome2);
    }

    @Test
    @DisplayName("Test Get Expense List")
    public void getExpenseList(){
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        accounting.addTransaction(expense1);
        assertEquals(1, accounting.getExpenseList().size());

        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        accounting.addTransaction(expense2);
        assertEquals(2, accounting.getExpenseList().size());

        Expense addedIncome1 = accounting.getExpenseList().get(0);
        assertEquals(expense1, addedIncome1);

        Expense addedIncome2 = accounting.getExpenseList().get(1);
        assertEquals(expense2, addedIncome2);
    }

    @Test
    @DisplayName("Test Get Transactions")
    public void getTransactionList(){
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        accounting.addTransaction(expense1);
        assertEquals(1, accounting.getTransactions().size());

        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        accounting.addTransaction(income1);
        assertEquals(2, accounting.getTransactions().size());
    }

    @Test
    @DisplayName("Test Get Total Expense")
    public void getTotalExpense(){
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        Expense expense2 = new Expense("test2", "test2", 75, LocalDate.now());
        accounting.addTransaction(expense1);
        accounting.addTransaction(expense2);
        assertEquals(175, accounting.getTotalExpense());
    }

    @Test
    @DisplayName("Test Get Total Income")
    public void getTotalIncome(){
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        Income income2 = new Income("test2", "test2", 99, LocalDate.now());
        accounting.addTransaction(income1);
        accounting.addTransaction(income2);
        assertEquals(199, accounting.getTotalIncome());
    }

    @Test
    @DisplayName("Test Deep Copy")
    public void testAccountingDeepCopy(){
        Accounting accounting2 = new Accounting(accounting);
        assertEquals(accounting, accounting2);
    }


}
