package no.ntnu.idatt1002.app.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExpenseTest {

    private Expense income;

    @BeforeEach
    void setUp() {
        income = new Expense("test", "test", 100, LocalDate.now());
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
        assertEquals(100, income.getAmount());
    }

    @Test
    void testGetCategory() {
        assertEquals("test", income.getCategory());
    }

    @Test
    void testGetDate() {
        LocalDate time = LocalDate.now();
        Expense dateTestExpense = new Expense("test", "test", 100, time);
        assertEquals(time, dateTestExpense.getDate());
    }

    @Test
    void testGetDescription() {
        assertEquals("test", income.getDescription());
    }

    @Test
    void testSetAmount() {
        assertThrows(IllegalArgumentException.class, () -> income.setAmount(-1));
        assertEquals(100, income.getAmount());
    }

    @Test
    void testSetCategory() {
        assertThrows(IllegalArgumentException.class, () -> income.setCategory(null));
        assertThrows(IllegalArgumentException.class, () -> income.setCategory(""));
        assertEquals("test", income.getCategory());
    }

    @Test
    void testSetDate() {
        LocalDate time = LocalDate.now();
        Expense dateTestExpense = new Expense("test", "test", 100, time);
        assertEquals(time, dateTestExpense.getDate());
    }

    @Test
    void testSetDescription() {
        assertThrows(IllegalArgumentException.class, () -> income.setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> income.setDescription(""));
        assertEquals("test", income.getDescription());
    }
}
