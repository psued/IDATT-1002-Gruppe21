package no.ntnu.idatt1002.app;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.registries.ProjectRegistry;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;

/**
 * A User class representing a user with a collection of projects. It provides
 * methods to
 * add, remove, and edit projects, as well as to save the user's data to a file.
 *
 */
public class User implements Serializable {

  private static User instance;

  private final ProjectRegistry projectRegistry;

  private User(){
    projectRegistry = new ProjectRegistry();
  }

  public static User getInstance() {
    if (instance == null) {
      instance = new User();
    }
    return instance;
  }

  /**
   * Constructs a User object with an empty ProjectRegistry.
   */
  //public User() {
    //this.projectRegistry = new ProjectRegistry();
  //}

  /**
   * Returns the user's project registry.
   *
   * @return The user's ProjectRegistry.
   */
  public ProjectRegistry getProjectRegistry() {
    return projectRegistry;
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
