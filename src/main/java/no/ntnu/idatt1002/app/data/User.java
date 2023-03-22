package no.ntnu.idatt1002.app.data;

import no.ntnu.idatt1002.app.registry.ProjectRegistry;

public class User {
  private ProjectRegistry projectRegistry = new ProjectRegistry();
  
  public ProjectRegistry getProjectRegistry() {
    return projectRegistry;
  }
  
  public void addProject(Project project) {
    projectRegistry.addProject(project);
  }
}
