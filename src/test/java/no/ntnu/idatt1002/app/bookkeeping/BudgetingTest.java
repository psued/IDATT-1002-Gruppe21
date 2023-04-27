package no.ntnu.idatt1002.app.bookkeeping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt1002.app.bookkeeping.Budgeting;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

public class BudgetingTest {

    private Budgeting budgeting;

    @BeforeEach
    public void setUp() {
        budgeting = new Budgeting();
    }

    @Test
    @DisplayName("Test Add Transaction")
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
    @DisplayName("Test Get Expense List")
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
    @DisplayName("Test Get Income List")
    public void testGetIncomeList() {
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        budgeting.addTransaction(income1);
        assertEquals(1, budgeting.getIncomeList().size());
        
        Income income2 = new Income("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(income2);
        assertEquals(2, budgeting.getIncomeList().size());

        Income addedIncome1 = budgeting.getIncomeList().get(0);
        assertEquals(income1, addedIncome1);

        Income addedIncome2 = budgeting.getIncomeList().get(1);
        assertEquals(income2, addedIncome2);
    }

    @Test
    @DisplayName("Test Get Total Expense")
    public void testGetTotalExpense() {
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(expense1);
        budgeting.addTransaction(expense2);

        assertEquals(200, budgeting.getTotalExpense());
    }

    @Test
    @DisplayName("Test Get Total Income")
    public void testGetTotalIncome() {
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        Income income2 = new Income("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(income1);
        budgeting.addTransaction(income2);

        assertEquals(200, budgeting.getTotalIncome());
    }

    @Test
    @DisplayName("Test Remove Transaction")
    public void testRemoveExpense() {
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        budgeting.addTransaction(expense1);
        assertEquals(1, budgeting.getExpenseList().size());
        budgeting.removeTransaction(expense1);
        assertEquals(0, budgeting.getExpenseList().size());
    }

    @Test
    @DisplayName("Test Update Transaction")
    public void testUpdateExpense(){
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        Income income2 = new Income("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(expense1);
        budgeting.addTransaction(income1);
        budgeting.updateTransaction(income1, expense2);
        assertEquals(expense2, budgeting.getTransactions().get(1));
        budgeting.updateTransaction(expense1, income2);
        assertEquals(income2, budgeting.getTransactions().get(0));


    }

    @Test
    @DisplayName("Test Get Income List")
    public void getIncomeList(){
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        budgeting.addTransaction(income1);
        assertEquals(1, budgeting.getIncomeList().size());

        Income income2 = new Income("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(income2);
        assertEquals(2, budgeting.getIncomeList().size());

        Income addedIncome1 = budgeting.getIncomeList().get(0);
        assertEquals(income1, addedIncome1);

        Income addedIncome2 = budgeting.getIncomeList().get(1);
        assertEquals(income2, addedIncome2);
    }

    @Test
    @DisplayName("Test Get Expense List")
    public void getExpenseList(){
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        budgeting.addTransaction(expense1);
        assertEquals(1, budgeting.getExpenseList().size());

        Expense expense2 = new Expense("test2", "test2", 100, LocalDate.now());
        budgeting.addTransaction(expense2);
        assertEquals(2, budgeting.getExpenseList().size());

        Expense addedIncome1 = budgeting.getExpenseList().get(0);
        assertEquals(expense1, addedIncome1);

        Expense addedIncome2 = budgeting.getExpenseList().get(1);
        assertEquals(expense2, addedIncome2);
    }

    @Test
    @DisplayName("Test Get Transactions")
    public void getTransactionList(){
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        budgeting.addTransaction(expense1);
        assertEquals(1, budgeting.getTransactions().size());

        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        budgeting.addTransaction(income1);
        assertEquals(2, budgeting.getTransactions().size());
    }

    @Test
    @DisplayName("Test Get Total Expense")
    public void getTotalExpense(){
        Expense expense1 = new Expense("test1", "test1", 100, LocalDate.now());
        Expense expense2 = new Expense("test2", "test2", 75, LocalDate.now());
        budgeting.addTransaction(expense1);
        budgeting.addTransaction(expense2);
        assertEquals(175, budgeting.getTotalExpense());
    }

    @Test
    @DisplayName("Test Get Total Income")
    public void getTotalIncome(){
        Income income1 = new Income("test1", "test1", 100, LocalDate.now());
        Income income2 = new Income("test2", "test2", 99, LocalDate.now());
        budgeting.addTransaction(income1);
        budgeting.addTransaction(income2);
        assertEquals(199, budgeting.getTotalIncome());
    }


    @Test
    @DisplayName("Test Deep Copy Test")
    public void testAccountingDeepCopy(){
        Budgeting budgeting2 = new Budgeting(budgeting);
        assertEquals(budgeting, budgeting2);
    }
}
