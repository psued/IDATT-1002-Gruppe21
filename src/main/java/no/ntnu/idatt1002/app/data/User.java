package no.ntnu.idatt1002.app.data;

import java.time.LocalDate;
import java.util.ArrayList;

import no.ntnu.idatt1002.app.registry.ProjectRegistry;

public class User {

  private ProjectRegistry projectRegistry = new ProjectRegistry();

  public ProjectRegistry getProjectRegistry() {
    return projectRegistry;
  }

  // NOTE(ingar): We will probably just send in empty arraylists if nothing is
  // entered
  public void addProject(String name, String description, String category, LocalDate dueDate,
      ArrayList<Expense> accountingExpenses, ArrayList<Equity> accountingEquities, ArrayList<Expense> budgetingExpenses,
      ArrayList<Equity> budgetingEquities) {
    Project newProject = new Project(name, description, category, dueDate);

    newProject.getAccounting().addExpenses(accountingExpenses);
    newProject.getAccounting().addEquities(accountingEquities);

    newProject.getBudgeting().addExpenses(budgetingExpenses);
    newProject.getBudgeting().addEquities(budgetingEquities);

    projectRegistry.addProject(newProject);
  }

  // // TODO
  // public void addProject(String name, String description, String category) {
  // projectRegistry.addProject(new Project(name, description, category));
  // }

  public void removeProject(Project project) {
    projectRegistry.removeProject(project);
  }

  public void editProject(Project project, String name, String description, String category) {
    project.setName(name);
    project.setDescription(description);
    project.setCategory(category);
  }

  public void saveToFile() {
    // TODO(ingar): implement with classes made by Lars
    // will probably save the projectRegistry
  }

  public void loadFromFile() {
    // TODO(ingar): implement with classes made by Lars
    // Will probably load the projectRegistry
  }

}
