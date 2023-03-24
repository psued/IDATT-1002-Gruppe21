package no.ntnu.idatt1002.app.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Registry class for all the projects for a user
 */
public class ProjectRegistry implements Serializable {

  private final ArrayList<Project> projects;
  private final List<String> categories = new ArrayList<>();

  /**
   * Creates a new project registry
   */
  public ProjectRegistry() {
    this.projects = new ArrayList<>();
    categories.add("Freelance");
    categories.add("Personal");
  }

  /**
   * Adds a project to the registry
   *
   * @param project the project to be added
   */
  public void addProject(Project project) {
    projects.add(project);
  }

  /**
   * Gets all the projects from the registry
   *
   * @return the projects
   */
  public ArrayList<Project> getProjects() {
    return new ArrayList<>(projects);
  }

  /**
   * Gets all the categories from the registry
   *
   * @return the categories
   */
  public List<String> getCategories() {
    return categories;
  }

  public void removeProject(Project project) {
    projects.remove(project);
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

  /**
   * Returns a string representation of the projectregistry object.
   *
   * @return A string representation of the projectregistry object.
   */
  @Override
  public String toString() {
    return "ProjectRegistry{" +
      "projects=" + projects +
      ", categories=" + categories +
      '}';
  }
}
