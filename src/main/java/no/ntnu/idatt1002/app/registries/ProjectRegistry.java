package no.ntnu.idatt1002.app.registries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.app.registers.Project;

/**
 * The ProjectRegistry class represents a registry of {@link Project projects}. It contains
 * methods for adding, removing and updating projects, as well as getting a deep copy of all
 * projects.
 *
 * <p>The class also stores the possible categories and statuses that a project can have, where
 * the different categories can be updated, but the statuses are fixed.
 *
 * @see Project
 */
public class ProjectRegistry implements Serializable {
  
  private final ArrayList<Project> projects;
  private final List<String> categories = new ArrayList<>();
  private final List<String> statuses = new ArrayList<>();
  
  /**
   * Constructor for the ProjectRegistry. Initializes the registry with preset categories
   * and statuses and sets the projects to an empty ArrayList.
   */
  public ProjectRegistry() {
    categories.add("Freelance");
    categories.add("Personal");
    categories.add("Miscellaneous");

    statuses.add("No status");
    statuses.add("Not started");
    statuses.add("Doing");
    statuses.add("Finished");
    
    projects = new ArrayList<>();
  }
  
  /**
   * Add a project to the registry.
   *
   * @param project the project to be added
   * @throws IllegalArgumentException if the project is null or if the project already exists in
   */
  public void addProject(Project project) throws IllegalArgumentException {
    if (project == null) {
      throw new IllegalArgumentException("project cannot be null");
    } else if (projects.contains(project)) {
      throw new IllegalArgumentException("project already exists in the registry");
    }
    projects.add(project);
  }
  
  /**
   * Remove a project from the registry.
   *
   * @param project the project to be removed
   * @throws IllegalArgumentException if the project is null
   */
  public void removeProject(Project project) throws IllegalArgumentException {
    if (project == null) {
      throw new IllegalArgumentException("project cannot be null");
    }
    projects.remove(project);
  }
  
  /**
   * Updates a project in the registry. Does this by getting the index of the old project and
   * updating the project at that index to the new project.
   *
   * @param oldProject the old project that is still in the registry
   * @param newProject the project to be updated to
   * @throws IllegalArgumentException if either of the projects are null or if the old project
   *                                  does not exist in the registry
   */
  public void updateProject(Project oldProject, Project newProject)
      throws IllegalArgumentException {
    if (oldProject == null || newProject == null) {
      throw new IllegalArgumentException("project cannot be null");
    }
    try {
      projects.set(projects.indexOf(oldProject), newProject);
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("oldProject is not in the registry");
    }
  }
  
  /**
   * Get a deep copy of all projects in the registry. Achieves this by creating a new ArrayList
   * and running each project through the project deep copy constructor.
   *
   * @return a deep copy list of all projects in the registry
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
   * @throws IllegalArgumentException if the category is null
   */
  public void addCategory(String category) throws IllegalArgumentException {
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }
    categories.add(category);
  }
  
  /**
   * Gets all the categories that are in the registry.
   *
   * @return all the categories in the registry
   */
  public List<String> getCategories() {
    return new ArrayList<>(categories);
  }
  
  /**
   * Removes a category from the registry.
   *
   * @param category the category to be removed
   * @throws IllegalArgumentException if the category is null or if the category is in use by a
   *                                  project
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
   * Gets all the statuses that are in the registry.
   *
   * @return all the statuses in the registry
   */
  public List<String> getStatuses() {
    return new ArrayList<>(statuses);
  }
}
