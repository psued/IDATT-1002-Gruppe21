package no.ntnu.idatt1002.app.registries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.registries.ProjectRegistry;

public class ProjectRegistryTest {

    private ProjectRegistry projectRegistry;

    @BeforeEach
    void setUp() {
        projectRegistry = new ProjectRegistry();
    }

    @Test
    @DisplayName("Test Add Category")
    void testAddCategory() {
        projectRegistry.addCategory("test");
        ArrayList<String> categories = new ArrayList<>(projectRegistry.getCategories());
        assertEquals("test", categories.get(projectRegistry.getCategories().size() - 1));
    }
    
    @Test
    @DisplayName("Test Remove Category")
    void testRemoveCategory() {
        projectRegistry.addCategory("test");
        projectRegistry.removeCategory("test");
        ArrayList<String> categories = new ArrayList<>(projectRegistry.getCategories());
        assertEquals("Miscellaneous", categories.get(projectRegistry.getCategories().size() - 1));
    }
    
    @Test
    @DisplayName("Test Get Categories")
    void testGetCategories() {
        ArrayList<String> categories = new ArrayList<>(projectRegistry.getCategories());
        assertEquals("Freelance", categories.get(0));
        assertEquals("Personal", categories.get(1));
    }

    @Test
    @DisplayName("Test Add Project")
    void testAddProject() {
        Project project = new Project("name", "description", "category", LocalDate.now(), "TestStatus");
        projectRegistry.addProject(project);
        assertTrue(projectRegistry.getProjects().contains(project));
    }
    
    @Test
    @DisplayName("Test Remove Project")
    void testRemoveProject() {
        Project project = new Project("name", "description", "category", LocalDate.now(), "TestStatus");
        projectRegistry.addProject(project);
        projectRegistry.removeProject(project);
        assertTrue(!projectRegistry.getProjects().contains(project));
    }

    @Test
    @DisplayName("Test Get Projects")
    void testGetProjects() {
        Project project1 = new Project("name", "description", "category", LocalDate.now(), "TestStatus");
        Project project2 = new Project("name2", "description2", "category2", LocalDate.now(), "TestStatus");

        projectRegistry.addProject(project1);
        projectRegistry.addProject(project2);

        ArrayList<Project> projects = new ArrayList<>(projectRegistry.getProjects());
        assertEquals(2, projects.size());
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));
    }

    @Test
    @DisplayName("Test Get Statuses")
    void testGetStatuses() {
        ArrayList<String> statuses = new ArrayList<>(projectRegistry.getStatuses());
        assertEquals("No status", statuses.get(0));
        assertEquals("Not started", statuses.get(1));
    }

    @Test
    @DisplayName("Test Update Project")
    void testUpdateProject() {
        Project project = new Project("name", "description", "category", LocalDate.now(), "TestStatus");
        Project project2 = new Project("name2", "description2", "category2", LocalDate.now(), "TestStatus2");
        projectRegistry.addProject(project);
        assertTrue(projectRegistry.getProjects().contains(project));
        projectRegistry.updateProject(project, project2);
        assertTrue(projectRegistry.getProjects().contains(project2));
    }
}
