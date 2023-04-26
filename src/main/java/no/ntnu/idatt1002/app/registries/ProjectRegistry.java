package no.ntnu.idatt1002.app.registries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.app.registers.Project;

/**
 * Registry class for all projects. Provides methods to add and remove projects.
 */
public class ProjectRegistry implements Serializable {

  private final ArrayList<Project> projects;
  private final List<String> categories = new ArrayList<>();
  private final List<String> statuses = new ArrayList<>();

  /**
   * Constructor for ProjectRegistry. Initializes the registry with preset categories and statuses.
   */
  public ProjectRegistry() {
    this.projects = new ArrayList<>();
    categories.add("Freelance");
    categories.add("Personal");
    categories.add("Miscellaneous");

    statuses.add("Not started");
    statuses.add("Doing");
    statuses.add("Finished");
  }

  /**
   * Adds a project to the registry.
   *
   * @param project the project to be added
   */
  public void addProject(Project project) throws IllegalArgumentException {
    if (project == null) {
      throw new IllegalArgumentException("project cannot be null");
    }
    projects.add(project);
  }
  
  /**
   * Removes a project from the registry.
   *
   * @param project the project to be removed
   */
  public void removeProject(Project project) throws IllegalArgumentException {
    if (project == null) {
      throw new IllegalArgumentException("project cannot be null");
    }
    projects.remove(project);
  }
  
  /**
   * Updates a project in the registry.
   *
   * @param oldProject the old project that is still in the registry
   * @param newProject the project to be updated to
   */
  public void updateProject(Project oldProject, Project newProject) throws
      IllegalArgumentException {
    if (oldProject == null || newProject == null) {
      throw new IllegalArgumentException("project cannot be null");
    }
    projects.set(projects.indexOf(oldProject), newProject);
  }
  
  /**
   * Gets a deep copy of all projects from the registry.
   *
   * @return the projects
   */
  public List<Project> getProjects() {
    List<Project> copy = new ArrayList<>();
    for (Project project : projects) {
      copy.add(new Project(project));
    }
    return copy;
  }
  
  /**
   * Adds a category to the registry.
   *
   * @param category the category to be added
   */
  public void addCategory(String category) throws IllegalArgumentException {
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }
    categories.add(category);
  }
  
  /**
   * Gets all categories from the registry.
   *
   * @return the categories
   */
  public List<String> getCategories() {
    return new ArrayList<>(categories);
  }

  /**
   * Removes a category from the registry.
   *
   * @param category the category to be removed
   */
  public void removeCategory(String category) throws IllegalArgumentException {
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    } else if (getProjects().stream().anyMatch(project -> project.getCategory().equals(category))) {
      throw new IllegalArgumentException("Cannot remove a category that is in use by a project");
    }
    categories.remove(category);
  }

  /**
   * Gets all statuses from the registry
   *
   * @return the statuses
   */
  public List<String> getStatuses() {
    return new ArrayList<>(statuses);
  }
}
