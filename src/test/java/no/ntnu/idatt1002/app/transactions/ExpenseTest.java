package no.ntnu.idatt1002.app.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpenseTest {

    private Expense expense;

    @BeforeEach
    void setUp() {
        expense = new Expense("test", "test", 100, LocalDate.now());
    }


    @Test
    public void testExpense() {
        assertThrows(IllegalArgumentException.class, () -> new Expense(null, "test", 100, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Expense("", "test", 100, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Expense("test", null, 100, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Expense("test", "", 100, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Expense("test", "test", -1, LocalDate.now()));
    }

    @Test
    void testGetAmount() {
        assertEquals(100, expense.getAmount());
    }

    @Test
    void testGetCategory() {
        assertEquals("test", expense.getCategory());
    }

    @Test
    void testGetDate() {
        LocalDate time = LocalDate.now();
        Expense dateTestExpense = new Expense("test", "test", 100, time);
        assertEquals(time, dateTestExpense.getDate());
    }

    @Test
    void testGetDescription() {
        assertEquals("test", expense.getDescription());
    }

    @Test
    void testSetAmount() {
        assertThrows(IllegalArgumentException.class, () -> expense.setAmount(-1));
        assertEquals(100, expense.getAmount());
    }

    @Test
    void testSetCategory() {
        assertThrows(IllegalArgumentException.class, () -> expense.setCategory(null));
        assertThrows(IllegalArgumentException.class, () -> expense.setCategory(""));
        assertEquals("test", expense.getCategory());
    }

    @Test
    void testSetDate() {
        LocalDate time = LocalDate.now();
        Expense dateTestExpense = new Expense("test", "test", 100, time);
        assertEquals(time, dateTestExpense.getDate());
    }

    @Test
    void testSetDescription() {
        assertThrows(IllegalArgumentException.class, () -> expense.setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> expense.setDescription(""));
        assertEquals("test", expense.getDescription());
    }
}
