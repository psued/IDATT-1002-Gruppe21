//getImageIndex() is not able to be tested since
// it is not possible to create an Image object from a file path in a test environment.

package no.ntnu.idatt1002.app.registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Test exception in constructor")
    void testProject() {
        assertThrows(IllegalArgumentException.class, () -> new Project(null, "test", "test", LocalDate.now(), "TestStatus"));
        assertThrows(IllegalArgumentException.class, () -> new Project("", "test", "test", LocalDate.now(), "TestStatus"));
        assertThrows(IllegalArgumentException.class, () -> new Project("test", "test", null, LocalDate.now(), "TestStatus"));
        assertThrows(IllegalArgumentException.class, () -> new Project("test", "test", "", LocalDate.now(), "TestStatus"));
        assertThrows(IllegalArgumentException.class, () -> new Project("test", "test", "test", LocalDate.now(), null));
        assertThrows(IllegalArgumentException.class, () -> new Project("test", "test", "test", LocalDate.now(), ""));
    }

    @Test
    @DisplayName("Test Get Accounting")
    void testGetAccounting() {
        Accounting accounting = project.getAccounting();
        accounting.addTransaction(new Expense("test", "test", 100, LocalDate.now()));
        assertEquals(100, accounting.getTotalExpense());
    }

    @Test
    @DisplayName("Test Get Budgeting")
    void testGetBudgeting() {
        Budgeting budgeting = project.getBudgeting();
        budgeting.addTransaction(new Expense("test", "test", 100, LocalDate.now()));
        assertEquals(100, budgeting.getTotalExpense());
    }

    @Test
    @DisplayName("Test Get Category")
    void testGetCategory() {
        assertEquals("category", project.getCategory());
    }
    
    @Test
    @DisplayName("Test Get Description")
    void testGetDescription() {
        assertEquals("description", project.getDescription());
    }

    @Test
    @DisplayName("Test Get Name")
    void testGetName() {
        assertEquals("name", project.getName());
    }

    @Test
    @DisplayName("Test Get Status")
    void testGetStatus() {
        assertEquals("TestStatus", project.getStatus());
    }

    @Test
    @DisplayName("Test Set Category")
    void testSetCategory() {
        project.setCategory("newCategory");
        assertEquals("newCategory", project.getCategory());
        assertThrows(IllegalArgumentException.class, () -> project.setCategory(null));
        assertThrows(IllegalArgumentException.class, () -> project.setCategory(""));
    }

    @Test
    @DisplayName("Test Set Description")
    void testSetDescription() {
        project.setDescription("newDescription");
        assertEquals("newDescription", project.getDescription());

    }

    @Test
    @DisplayName("Test Set Name")
    void testSetName() {
        project.setName("newName");
        assertEquals("newName", project.getName());
        assertThrows(IllegalArgumentException.class, () -> project.setName(null));
        assertThrows(IllegalArgumentException.class, () -> project.setName(""));
    }

    @Test
    @DisplayName("Test Set Status")
    void testSetStatus() {
        project.setStatus("newStatus");
        assertEquals("newStatus", project.getStatus());
        assertThrows(IllegalArgumentException.class, () -> project.setStatus(null));
        assertThrows(IllegalArgumentException.class, () -> project.setStatus(""));
    }

    @Test
    @DisplayName("Test Add Image")
    void testAddImage() {
        project.addImage(new File("/chair.jpg"));
        assertEquals(new File("/chair.jpg"), project.getImages().get(0));
        assertThrows(IllegalArgumentException.class, () -> project.addImage(null));
    }

    @Test
    @DisplayName("Test Remove Image")
    void testRemoveImage() {
        project.addImage(new File("/chair.jpg"));
        project.removeImage(new File("/chair.jpg"));
        assertEquals(0, project.getImages().size());
        assertThrows(IllegalArgumentException.class, () -> project.removeImage(null));
    }

}
