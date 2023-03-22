package no.ntnu.idatt1002.app.data;

import no.ntnu.idatt1002.app.registry.ProjectRegistry;

public class User {

  private ProjectRegistry projectRegistry = new ProjectRegistry();
  
  public ProjectRegistry getProjectRegistry() {
    return projectRegistry;
  }
  
  public void addProject(String name, String description, String category) {
    projectRegistry.addProject(new Project(name, description, category));
  }

  public void removeProject() {
    // TODO(ingar): Needs some parameter that can get the corresponding
    // project
  }

  public void saveToFile() {
    // TODO(ingar): implement with classes made by Lars
  }

  public void loadFromFile() {
    // TODO(ingar): implement with classes made by Lars
    // Return type should be 
  }



}
