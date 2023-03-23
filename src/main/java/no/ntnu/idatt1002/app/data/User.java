package no.ntnu.idatt1002.app.data;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import no.ntnu.idatt1002.app.fileHandling.FileHandling;

// NOTE(ingar): Make this a Singleton?
public class User implements Serializable {

  private ProjectRegistry projectRegistry;

  public User() {
    this.projectRegistry = new ProjectRegistry();
  }

  public ProjectRegistry getProjectRegistry() {
    return projectRegistry;
  }

  // NOTE(ingar): We will probably just send in empty arraylists if nothing is
  // entered
  public void addProject(String name, String description, String category, LocalDate dueDate,
      ArrayList<Expense> accountingExpenses, ArrayList<Income> accountingIncome, ArrayList<Expense> budgetingExpenses,
      ArrayList<Income> budgetingIncome) {
    Project newProject = new Project(name, description, category, dueDate);

    newProject.getAccounting().addExpenses(accountingExpenses);
    newProject.getAccounting().addEquities(accountingIncome);

    newProject.getBudgeting().addExpenses(budgetingExpenses);
    newProject.getBudgeting().addEquities(budgetingIncome);

    projectRegistry.addProject(newProject);
  }

  public void removeProject(Project project) {
    projectRegistry.removeProject(project);
  }

  public void editProject(Project project, String name, String description, String category, LocalDate dueDate,
      ArrayList<Expense> accountingExpenses, ArrayList<Income> accountingEquities, ArrayList<Expense> budgetingExpenses,
      ArrayList<Income> budgetingEquities) {
    removeProject(project);
    addProject(name, description, category, dueDate, accountingExpenses, accountingEquities, budgetingExpenses,
        budgetingEquities);
  }

  public void saveToFile() {
    try {
      FileHandling.writeUserToFile(this);
    } catch (IOException e) {
      // TODO(ingar): Need to give the user a message that something failed
      System.err.println("Error writing to file: " + e.getMessage());
      e.printStackTrace(); // NOTE(ingar): This might not be needed in the final product
    } catch (Exception e) {
      // NOTE(ingar): Same as above
      System.err.println("Error trying to write to file: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return "User{" +
      "projectRegistry=" + projectRegistry +
      '}';
  }
}
