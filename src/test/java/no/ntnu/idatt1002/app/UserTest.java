package no.ntnu.idatt1002.app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import no.ntnu.idatt1002.app.User;
import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

public class UserTest {

  private User user;

  @BeforeEach
  void setUp() {
    user = new User();
    
    ArrayList<Expense> accountingExpenses = new ArrayList<>();
    Expense expense1 = new Expense("Ticket to bus", "Transportation", 100, LocalDate.now());
    Expense expense2 = new Expense("Fuel", "Transportation", 100, LocalDate.now());
    accountingExpenses.add(expense1);
    accountingExpenses.add(expense2);
    
    ArrayList<Income> accountingIncome = new ArrayList<>();
    Income income1 = new Income("Salary", "Salary", 1000, LocalDate.now());
    Income income2 = new Income("Gift", "Gift", 1000, LocalDate.now());
    accountingIncome.add(income1);
    accountingIncome.add(income2);
    
    ArrayList<Expense> budgetingExpenses = new ArrayList<>();
    Expense expense3 = new Expense("Ticket to bus", "Transportation", 100, LocalDate.now());
    Expense expense4 = new Expense("Fuel", "Transportation", 100, LocalDate.now());
    budgetingExpenses.add(expense3);
    budgetingExpenses.add(expense4);
    
    ArrayList<Income> budgetingIncome = new ArrayList<>();
    Income income3 = new Income("Salary", "Salary", 1000, LocalDate.now());
    Income income4 = new Income("Gift", "Gift", 1000, LocalDate.now());
    budgetingIncome.add(income3);
    budgetingIncome.add(income4);

    user.addProject("TestName", "TestDescription", "TestCategory", LocalDate.now(),
      "TestStatus", accountingExpenses, accountingIncome, budgetingExpenses, budgetingIncome);
  }

  @Nested
  class AddProjectTest {

    @Test
    @DisplayName("Test that addProject throws IllegalArgumentException when given null values")
    void testAddProjectExceptions() {
      assertThrows(IllegalArgumentException.class,
          () -> user.addProject(null, "TestDescription", "TestCategory",
              LocalDate.now(), "TestStatus", new ArrayList<>(), new ArrayList<>(),
              new ArrayList<>(), new ArrayList<>()));

      assertThrows(IllegalArgumentException.class,
          () -> user.addProject("TestName", null, "TestCategory",
              LocalDate.now(), "TestStatus", new ArrayList<>(), new ArrayList<>(),
              new ArrayList<>(), new ArrayList<>()));

      assertThrows(IllegalArgumentException.class,
          () -> user.addProject("TestName", "TestDescription", null,
              LocalDate.now(), "TestStatus", new ArrayList<>(), new ArrayList<>(),
              new ArrayList<>(), new ArrayList<>()));

      assertThrows(IllegalArgumentException.class,
          () -> user.addProject("TestName", "TestDescription",
              "TestCategory", null, "TestStatus", new ArrayList<>(), new ArrayList<>(),
              new ArrayList<>(), new ArrayList<>()));

      assertThrows(IllegalArgumentException.class,
          () -> user.addProject("TestName", "TestDescription",
              "TestCategory", LocalDate.now(), null, new ArrayList<>(), new ArrayList<>(),
              new ArrayList<>(), new ArrayList<>()));

      assertThrows(IllegalArgumentException.class,
          () -> user.addProject("TestName", "TestDescription",
              "TestCategory", LocalDate.now(), "TestStatus", null, new ArrayList<>(),
              new ArrayList<>(), new ArrayList<>()));

      assertThrows(IllegalArgumentException.class,
        () -> user.addProject("TestName", "TestDescription",
          "TestCategory", LocalDate.now(), "TestStatus", new ArrayList<>(),
          null, new ArrayList<>(), new ArrayList<>()));

      assertThrows(IllegalArgumentException.class,
          () -> user.addProject("TestName", "TestDescription",
              "TestCategory", LocalDate.now(), "TestStatus", new ArrayList<>(),
              new ArrayList<>(), null, new ArrayList<>()));

      assertThrows(IllegalArgumentException.class,
          () -> user.addProject("TestName", "TestDescription",
              "TestCategory", LocalDate.now(), "TestStatus", new ArrayList<>(),
              new ArrayList<>(), new ArrayList<>(), null));

      assertDoesNotThrow(() -> user.addProject("TestName",
          "TestDescription", "TestCategory", LocalDate.now(), "TestStatus",
        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    @DisplayName("Test that addProject correctly adds a project to the projectRegistry")
    void testAddProject() {
      user.addProject("TestName", "TestDescription", "TestCategory", LocalDate.now(), "TestStatus",
          new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
      assertEquals(2, user.getProjectRegistry().getProjects().size());
      
      // ArrayLists are initialized when a project is created, and is therefore equal to the one
      // added above
      Project project = new Project("TestName", "TestDescription", "TestCategory", LocalDate.now(), "TestStatus");
      assertEquals(user.getProjectRegistry().getProjects().get(1), project);
    }
  }

  @Test
  @DisplayName("Test editProject")
  void testEditProject() {
    Project toEdit = user.getProjectRegistry().getProjects().get(0);
    user.editProject(toEdit, "NewName", "NewDescription", "NewCategory", LocalDate.now(),
      "TestStatus", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    
    Project sameeProject = new Project("NewName", "NewDescription", "NewCategory", LocalDate.now(), "TestStatus");
    assertEquals(user.getProjectRegistry().getProjects().get(0), sameeProject);
  }

  @Test
  void testRemoveProject() {
    Project toRemove = user.getProjectRegistry().getProjects().get(0);
    user.removeProject(toRemove);
    assertTrue(user.getProjectRegistry().getProjects().isEmpty());
  }

  @Test
  void testGetProjectRegistry() {
    User newUser = new User();
    newUser.addProject("TestName", "TestDescription", "TestCategory", LocalDate.now(), "TestStatus",
        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    Project project = new Project("TestName", "TestDescription", "TestCategory", LocalDate.now(), "TestStatus");
    assertEquals(newUser.getProjectRegistry().getProjects().get(0), project);
  }

}
