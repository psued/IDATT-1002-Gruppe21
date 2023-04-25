package no.ntnu.idatt1002.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import no.ntnu.idatt1002.app.registers.MonthlyBookkeeping;
import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserTest {

  private User user = User.getInstance();

  @BeforeEach
  void setUp() {
    
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

    Project currentproject = new Project("TestName", "TestDescription", "TestCategory",
            LocalDate.now(), "TestStatus");

    user.getProjectRegistry().addProject(currentproject);
  }

  @Nested
  class AddProjectTest {
    @Test
    @DisplayName("Test that addProject correctly adds a project to the projectRegistry")
    void testAddProject() {
      user.getProjectRegistry().addProject(new Project("Testname", "TestDescription",
          "TestCategory", LocalDate.now(), "TestStatus"));
      //assertEquals(3, user.getProjectRegistry().getProjects().size());
      
      // ArrayLists are initialized when a project is created, and is therefore equal to the one
      // added above
      Project project = new Project("TestName", "TestDescription", "TestCategory",
          LocalDate.now(), "TestStatus");
      assertEquals(user.getProjectRegistry().getProjects().get(1), project);
    }
  }

  @Test
  void testRemoveProject() {
    Project toRemove = user.getProjectRegistry().getProjects().get(0);
    user.getProjectRegistry().removeProject(toRemove);
    assertTrue(user.getProjectRegistry().getProjects().isEmpty());
  }




  @Test
  void testGetProjectRegistry() {
    //User newUser = User.getInstance();
    user.getProjectRegistry().addProject(new Project("TestName", "TestDescription", "TestCategory", LocalDate.now(), "TestStatus"));

    Project project = new Project("TestName", "TestDescription", "TestCategory", LocalDate.now(),
        "TestStatus");
    assertEquals(user.getProjectRegistry().getProjects().get(1), project);
  }

  @Test
  void singletonFuncitonality() {
    User user1 = User.getInstance();
    assertEquals(0, user1.getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().size());
    User user2 = User.getInstance();
    user2.getMonthlyBookkeepingRegistry().addMonthlyBookkeeping(new MonthlyBookkeeping(YearMonth.now()));
    assertEquals(user1.getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().size(),
        user2.getMonthlyBookkeepingRegistry().getMonthlyBookkeepingMap().size());
  }
}
