package no.ntnu.idatt1002.app;

import java.io.Serializable;
import no.ntnu.idatt1002.app.registries.MonthlyBookkeepingRegistry;
import no.ntnu.idatt1002.app.registries.ProjectRegistry;

/**
 * A User class representing a user with a collection of projects. It provides
 * methods to
 * add, remove, and edit projects, as well as to save the user's data to a file.
 */
public class User implements Serializable {

  private static User instance;

  private final ProjectRegistry projectRegistry;
  private final MonthlyBookkeepingRegistry monthlyBookkeepingRegistry;

  private User(){
    projectRegistry = new ProjectRegistry();
    monthlyBookkeepingRegistry = new MonthlyBookkeepingRegistry();
  }

  public static User getInstance() {
    if (instance == null) {
      instance = new User();
    }
    return instance;
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
   * Returns the user's monthly bookkeeping registry.
   *
   * @return The user's MonthlyBookkeepingRegistry.
   */
  public MonthlyBookkeepingRegistry getMonthlyBookkeepingRegistry() {
    return monthlyBookkeepingRegistry;
  }
}
