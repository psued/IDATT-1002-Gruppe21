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
      ArrayList<Expense> accountingExpenses, ArrayList<Income> accountingEquities, ArrayList<Expense> budgetingExpenses,
      ArrayList<Income> budgetingEquities) {
    Project newProject = new Project(name, description, category, dueDate);

    newProject.getAccounting().addExpenses(accountingExpenses);
    newProject.getAccounting().addEquities(accountingEquities);

    newProject.getBudgeting().addExpenses(budgetingExpenses);
    newProject.getBudgeting().addEquities(budgetingEquities);

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

  /**
   public void loadFromFile() {
   try {
   projectRegistry = FileHandling.readProjectRegistryFromFile();
   } catch (IOException e) {
   // TODO(ingar): Need to give the user a message that something failed
   System.err.println("Error reading from file: " + e.getMessage());
   e.printStackTrace(); // NOTE(ingar): This might not be needed in the final product
   } catch (ClassNotFoundException e) {
   // NOTE(ingar): Same as above
   // NOTE(ingar): Might not need to catch this specifically, since it's not
   // really useful for the user
   System.err.println("Could not find class: " + e.getMessage());
   e.printStackTrace();
   } catch (Exception e) {
   // NOTE(ingar): Same as above
   System.err.println("Error trying to read from file: " + e.getMessage());
   e.printStackTrace();
   }
   }


  @Override
  public void finalize() {
    saveToFile();
  }
  **/

  @Override
  public String toString() {
    return "User{" +
      "projectRegistry=" + projectRegistry +
      '}';
  }
}
