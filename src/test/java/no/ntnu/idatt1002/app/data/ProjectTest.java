package no.ntnu.idatt1002.app.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjectTest {

    private Project project;

    @BeforeEach
    public void setUp() {
        project = new Project("name", "description", "category", LocalDate.now());
    }

    @Test
    void testProject() {
        assertThrows(IllegalArgumentException.class, () -> new Project(null, "test", "test", LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> new Project("", "test", "test", LocalDate.now()));
    }

    @Test
    void testGetAccounting() {
        Accounting accounting = project.getAccounting();
        accounting.addExpense(new Expense("test", "test", 100, LocalDate.now()));
        assertEquals(100, accounting.getTotalExpense());
    }

    @Test
    void testGetBudgeting() {
        Budgeting budgeting = project.getBudgeting();
        budgeting.addExpense(new Expense("test", "test", 100, LocalDate.now()));
        assertEquals(100, budgeting.getTotalExpense());
    }

    @Test
    void testGetCategory() {
        assertEquals("category", project.getCategory());
    }

    @Test
    void testGetCreationDate() {
        // Not possible to test with current implementation
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
