package no.ntnu.idatt1002.app.registry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import no.ntnu.idatt1002.app.data.Project;

/**
 * Class for registering all the projects
 */
public class ProjectRegistry {
  
  private final HashMap<LocalDateTime, Project> projects;
  private final List<String> categories = new ArrayList<>();
  
  /**
   * Creates a new project registry
   */
  public ProjectRegistry() {
    this.projects = new HashMap<>();
    categories.add("Freelance");
    categories.add("Personal");
  }
  
  /**
   * Adds a project to the registry
   *
   * @param project the project to be added
   */
  public void addProject(Project project) {
    projects.put(project.getCreationDate(), project);
  }
  
  /**
   * Gets a project from the registry
   *
   * @return the project
   */
  public Project getProject(LocalDateTime creationDate) {
    return projects.get(creationDate);
  }
  
  
  /**
   * Gets all the projects from the registry
   *
   * @return the projects
   */
  public Collection<Project> getProjects() {
    return projects.values();
  }
  
  /**
   * Gets all the categories from the registry
   *
   * @return the categories
   */
  public List<String> getCategories() {
    return categories;
  }
  
  /**
   * Adds a category to the registry
   *
   * @param category the category to be added
   */
  public void addCategory(String category) {
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }
    categories.add(category);
  }
}
