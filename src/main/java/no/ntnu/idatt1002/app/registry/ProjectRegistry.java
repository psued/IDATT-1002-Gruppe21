package no.ntnu.idatt1002.app.registry;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import no.ntnu.idatt1002.app.data.Project;

/**
 * Class for registering all the projects
 */
public class ProjectRegistry {
  
  private final HashMap<LocalDateTime, Project> projects;
  
  /**
   * Creates a new project registry
   */
  public ProjectRegistry() {
    this.projects = new HashMap<>();
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
}
