package no.ntnu.idatt1002.app;

import no.ntnu.idatt1002.app.registers.Project;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {

  @Nested
  @DisplayName("Get Instance")
  class GetInstanceTest {
    User user;
    Project project;

    @BeforeEach
    void setUp() {
      User.getInstance();
      User.getInstance().getProjectRegistry().getProjects().clear();
      user = User.getInstance();
      project = new Project("Test Project", "Test Description", "Freelance", null, "Finished");
      user.getProjectRegistry().addProject(project);

    }

    @AfterEach
    void tearDown() {
      User.getInstance().loadUser(null);
    }


    @Test
    @DisplayName("Test Load User")
    void loadUser() {
      User.getInstance().loadUser(null);
      assertNotEquals(null, User.getInstance());
    }

    @Test
    @DisplayName("Test Get Project")
    void getProjectRegistry() {
      assertEquals(project, User.getInstance().getProjectRegistry().getProjects().get(0));
    }


    @Test
    @DisplayName("Test Get Instance Initialization")
    void getInstance() {
      assertNotEquals(null, User.getInstance());

      assertEquals(user.getProjectRegistry().getProjects().size(), User.getInstance().getProjectRegistry().getProjects().size());
    }
  }

  @Nested
  @DisplayName("Load Registers")
  class loadRegisters{

    @Test
    @DisplayName("Test Load Project Registry")
    void loadProjectRegistry(){
      User.getInstance().getProjectRegistry().getProjects().clear();
      Project project = new Project("Test Project", "Test Description", "Freelance", null, "Finished");
      User.getInstance().getProjectRegistry().addProject(project);
      User.getInstance().loadUser(null);
      assertEquals(0, User.getInstance().getProjectRegistry().getProjects().size());
    }

    @Test
    @DisplayName("Test Load Monthly Bookkeeping Registry")
    void loadMonthlyBookkeepingRegistry(){
      assertNotEquals(null, User.getInstance().getMonthlyBookkeepingRegistry());
    }

  }


}
