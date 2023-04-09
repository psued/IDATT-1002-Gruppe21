package no.ntnu.idatt1002.app.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Registry class for all roject for auser.
 */
public class ProjectRegistry implements Serializable {

  private final ArrayList<Project> projects;
  private final List<String> categories = new ArrayList<>();

  /**
   * Cre new projectregistry.
   */
  public ProjectRegistry() {
    this.projects = new ArrayList<>();
    categories.add("Freelance");
    categories.add("Personal");
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
   * Gets al rojects from theregistry.
   *
   * @return the projects
   */
  public ArrayList<Project> getProjects() {
    return new ArrayList<>(projects);
  }

  /**
   * Gets al ategories from theregistry.
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
   * Returns a string representation of the project registry object.
   *
   * @return A string representation of the project registry object.
   */
  @Override
  public String toString() {
    return "ProjectRegistry{ projects=" + projects + ", categories=" + categories + '}';
  }
}
