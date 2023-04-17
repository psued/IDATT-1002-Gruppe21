package no.ntnu.idatt1002.app.registries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
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
    void testAddCategory() {
        projectRegistry.addCategory("test");
        ArrayList<String> categories = new ArrayList<>(projectRegistry.getCategories());
        assertEquals("test", categories.get(projectRegistry.getCategories().size() - 1));
    }
    
    @Test
    void testRemoveCategory() {
        projectRegistry.addCategory("test");
        projectRegistry.removeCategory("test");
        ArrayList<String> categories = new ArrayList<>(projectRegistry.getCategories());
        assertEquals("Miscellaneous", categories.get(projectRegistry.getCategories().size() - 1));
    }
    
    @Test
    void testGetCategories() {
        ArrayList<String> categories = new ArrayList<>(projectRegistry.getCategories());
        assertEquals("Freelance", categories.get(0));
        assertEquals("Personal", categories.get(1));
    }

    @Test
    void testAddProject() {
        Project project = new Project("name", "description", "category", LocalDate.now());
        projectRegistry.addProject(project);
        assertTrue(projectRegistry.getProjects().contains(project));
    }
    
    @Test
    void testRemoveProject() {
        Project project = new Project("name", "description", "category", LocalDate.now());
        projectRegistry.addProject(project);
        projectRegistry.removeProject(project);
        assertTrue(!projectRegistry.getProjects().contains(project));
    }

    @Test
    void testGetProjects() {
        Project project1 = new Project("name", "description", "category", LocalDate.now());
        Project project2 = new Project("name2", "description2", "category2", LocalDate.now());

        projectRegistry.addProject(project1);
        projectRegistry.addProject(project2);

        ArrayList<Project> projects = new ArrayList<>(projectRegistry.getProjects());
        assertEquals(2, projects.size());
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));

    }
}
