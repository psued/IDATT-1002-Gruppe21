package no.ntnu.idatt1002.app.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A User class representing a user with a collection of projects. It provides
 * methods to
 * add, remove, and edit projects, as well as to save the user's data to a file.
 *
 */
public class User implements Serializable {

  private final ProjectRegistry projectRegistry;

  /**
   * Constructs a User object with an empty ProjectRegistry.
   */
  public User() {
    this.projectRegistry = new ProjectRegistry();
  }

  /**
   * Returns the user's project registry.
   *
   * @return The user's ProjectRegistry.
   */
  public ProjectRegistry getProjectRegistry() {
    return projectRegistry;
  }

  /**
   * Adds a project to the user's project registry.
   *
   * @param name               The name of the project.
   * @param description        The description of the project.
   * @param category           The category of the project.
   * @param dueDate            The due date of the project.
   * @param accountingExpenses A list of accounting expenses.
   * @param accountingIncome   A list of accounting income.
   * @param budgetingExpenses  A list of budgeting expenses.
   * @param budgetingIncome    A list of budgeting income.
   * @throws IllegalArgumentException If one or more arguments are null.
   */
  public void addProject(String name, String description, String category, LocalDate dueDate,
      ArrayList<Expense> accountingExpenses, ArrayList<Income> accountingIncome,
         ArrayList<Expense> budgetingExpenses, ArrayList<Income> budgetingIncome) {
    if (name == null || description == null || category == null || dueDate == null
        || accountingExpenses == null || accountingIncome == null || budgetingExpenses == null
        || budgetingIncome == null) {
      throw new IllegalArgumentException("One or more arguments are null");
    }

    Project newProject = new Project(name, description, category, dueDate);

    newProject.getAccounting().addExpenses(accountingExpenses);
    newProject.getAccounting().addEquities(accountingIncome);

    newProject.getBudgeting().addExpenses(budgetingExpenses);
    newProject.getBudgeting().addEquities(budgetingIncome);

    projectRegistry.addProject(newProject);
  }
  
  public void addProject(Project project) {
    projectRegistry.addProject(project);
  }

  /**
   * Removes a project from the user's project registry.
   *
   * @param project The project to be removed.
   */
  public void removeProject(Project project) {
    projectRegistry.removeProject(project);
  }

  /**
   * Edits an existing project in the user's project registry.
   *
   * @param project            The project to be edited.
   * @param name               The new name of the project.
   * @param description        The new description of the project.
   * @param category           The new category of the project.
   * @param dueDate            The new due date of the project.
   * @param accountingExpenses A list of the new accounting expenses.
   * @param accountingEquities A list of the new accounting equities.
   * @param budgetingExpenses  A list of the new budgeting expenses.
   * @param budgetingEquities  A list of the new budgeting equities.
   */
  public void editProject(Project project, String name, String description, String category,
        LocalDate dueDate, ArrayList<Expense> accountingExpenses,
        ArrayList<Income> accountingEquities, ArrayList<Expense> budgetingExpenses,
        ArrayList<Income> budgetingEquities) {
    removeProject(project);
    addProject(name, description, category, dueDate, accountingExpenses, accountingEquities,
        budgetingExpenses, budgetingEquities);
  }
  
  /**
   * Returns a string representation of the user object.
   *
   * @return A string representation of the user object.
   */
  @Override
  public String toString() {
    return "User{projectRegistry=" + projectRegistry + '}';
  }
}
