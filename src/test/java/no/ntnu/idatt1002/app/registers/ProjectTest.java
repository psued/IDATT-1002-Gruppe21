package no.ntnu.idatt1002.app.registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;
import no.ntnu.idatt1002.app.transactions.Expense;

public class ProjectTest {

    private Project project;

    @BeforeEach
    public void setUp() {
        project = new Project("name", "description", "category", LocalDate.now(), "TestStatus");
    }

    @Test
    void testProject() {
        assertThrows(IllegalArgumentException.class, () -> new Project(null, "test", "test", LocalDate.now(), "TestStatus"));
        assertThrows(IllegalArgumentException.class, () -> new Project("", "test", "test", LocalDate.now(), "TestStatus"));
    }

    @Test
    void testGetAccounting() {
        Accounting accounting = project.getAccounting();
        accounting.addTransaction(new Expense("test", "test", 100, LocalDate.now()));
        assertEquals(100, accounting.getTotalExpense());
    }

    @Test
    void testGetBudgeting() {
        Budgeting budgeting = project.getBudgeting();
        budgeting.addTransaction(new Expense("test", "test", 100, LocalDate.now()));
        assertEquals(100, budgeting.getTotalExpense());
    }

    @Test
    void testGetCategory() {
        assertEquals("category", project.getCategory());
    }
    
    @Test
    void testGetDescription() {
        assertEquals("description", project.getDescription());
    }

    @Test
    void testGetName() {
        assertEquals("name", project.getName());
    }

    @Test
    void testSetCategory() {
        project.setCategory("newCategory");
        assertEquals("newCategory", project.getCategory());
    }

    @Test
    void testSetDescription() {
        project.setDescription("newDescription");
        assertEquals("newDescription", project.getDescription());
    }

    @Test
    void testSetName() {
        project.setName("newName");
        assertEquals("newName", project.getName());
    }
}
