package no.ntnu.idatt1002.app.data;

import java.util.ArrayList;

import no.ntnu.idatt1002.app.registry.ProjectRegistry;

public class User {

  private ProjectRegistry projectRegistry = new ProjectRegistry();
  
  public ProjectRegistry getProjectRegistry() {
    return projectRegistry;
  }
  
  public void addProject(String name, String description, String category, ArrayList<Expense> accountingExpenses, ArrayList<Income> accountingEquities, ArrayList<Expense> budgetingExpenses, ArrayList<Income> budgetingEquities) {
    Project newProject = new Project(name, description, category);

    newProject.getAccounting().addExpenses(accountingExpenses);
    newProject.getAccounting().addEquities(accountingEquities);

    newProject.getBudgeting().addExpenses(budgetingExpenses);
    newProject.getBudgeting().addEquities(budgetingEquities);

    projectRegistry.addProject(newProject);
  }

  // TODO
  public void addProject(String name, String description, String category) {
    projectRegistry.addProject(new Project(name, description, category));
  }

  public void removeProject() {
    // TODO(ingar): Needs some parameter that can get the corresponding
    // project
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
