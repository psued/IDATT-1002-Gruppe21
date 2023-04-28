package no.ntnu.idatt1002.app.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt1002.app.transactions.Income;

public class IncomeTest {

    private Income income;

    @BeforeEach
    void setUp() {
        income = new Income("test", "test", 100, LocalDate.now());
    }


    @Test
    @DisplayName("Test Income")
    public void testIncome() {
        assertThrows(IllegalArgumentException.class, () -> new Income(null, "test", 100, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Income("", "test", 100, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Income("test", null, 100, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Income("test", "", 100, LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Income("test", "test", -1, LocalDate.now()));
    }

    @Test
    @DisplayName("Test Income Deep Copy")
    public void testIncomeDeepCopy() {
        Income income2 = new Income(income);
        assertEquals(income.getAmount(), income2.getAmount());
        assertEquals(income.getCategory(), income2.getCategory());
        assertEquals(income.getDate(), income2.getDate());
        assertEquals(income.getDescription(), income2.getDescription());
    }

    @Test
    @DisplayName("Test Get Amount")
    void testGetAmount() {
        assertEquals(100, income.getAmount());
    }

    @Test
    @DisplayName("Test Get Category")
    void testGetCategory() {
        assertEquals("test", income.getCategory());
    }

    @Test
    @DisplayName("Test Get Date")
    void testGetDate() {
        LocalDate time = LocalDate.now();
        Income dateTestIncome = new Income("test", "test", 100, time);
        assertEquals(time, dateTestIncome.getDate());
    }

    @Test
    @DisplayName("Test Get Description")
    void testGetDescription() {
        assertEquals("test", income.getDescription());
    }

    @Test
    @DisplayName("Test Set Amount")
    void testSetAmount() {
        assertThrows(IllegalArgumentException.class, () -> income.setAmount(-1));
        assertEquals(100, income.getAmount());
    }

    @Test
    @DisplayName("Test Set Category")
    void testSetCategory() {
        assertThrows(IllegalArgumentException.class, () -> income.setCategory(null));
        assertThrows(IllegalArgumentException.class, () -> income.setCategory(""));
        assertEquals("test", income.getCategory());
    }

    @Test
    @DisplayName("Test Set Date")
    void testSetDate() {
        LocalDate time = LocalDate.now();
        Income dateTestIncome = new Income("test", "test", 100, time);
        assertEquals(time, dateTestIncome.getDate());
    }

    @Test
    @DisplayName("Test Set Description")
    void testSetDescription() {
        assertThrows(IllegalArgumentException.class, () -> income.setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> income.setDescription(""));
        assertEquals("test", income.getDescription());
    }
}
