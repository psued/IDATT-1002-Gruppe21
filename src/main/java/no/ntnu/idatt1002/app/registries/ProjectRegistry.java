package no.ntnu.idatt1002.app.registries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import no.ntnu.idatt1002.app.registers.Project;

/**
 * Registry class for all roject for auser.
 */
public class ProjectRegistry implements Serializable {

  private final ArrayList<Project> projects;
  private final List<String> categories = new ArrayList<>();
  private final List<String> statuses = new ArrayList<>();

  /**
   * Cre new projectregistry.
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
   * A roject to theregistry.
   *
   * @param project the project to be added
   */
  public void addProject(Project project) {
    projects.add(project);
  }

  /**
   * Gets all projects from the registry.
   *
   * @return the projects
   */
  public List<Project> getProjects() {
    return new ArrayList<>(projects);
  }
  
  /**
   * Removes a project from the registry.
   *
   * @param project the project to be removed
   */
  public void removeProject(Project project) {
    projects.remove(project);
  }
  
  /**
   * Adds a category to the registry.
   *
   * @param category the category to be added
   */
  public void addCategory(String category) {
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
   * Gets all statuses from the registry
   *
   * @return the statuses
   */
  public List<String> getStatuses() {
    return new ArrayList<>(statuses);
  }
  
  /**
   * Removes a category from the registry.
   *
   * @param category the category to be removed
   */
  public void removeCategory(String category) {
    if (getProjects().stream().anyMatch(project -> project.getCategory().equals(category))) {
      throw new IllegalArgumentException("Cannot remove a category that is in use by a project");
    }
    categories.remove(category);
  }

  /**
   * Returns a string representation of the project registry object.
   *
   * @return A string representation of the project registry object.
   */
  @Override
  public String toString() {
    return "ProjectRegistry{ projects=" + projects + ", categories=" + categories + '}';
  }
}
