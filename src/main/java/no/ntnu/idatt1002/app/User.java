package no.ntnu.idatt1002.app;

import java.io.Serializable;
import no.ntnu.idatt1002.app.registries.MonthlyBookkeepingRegistry;
import no.ntnu.idatt1002.app.registries.ProjectRegistry;

/**
 * The user class represents all the information that the user has in the application. This means
 * that the user class contains the user's {@link ProjectRegistry} and
 * {@link MonthlyBookkeepingRegistry}.
 *
 * <p>The user class is a singleton class, meaning that there can only be one instance of the
 * class can exist at any point. This results in the programs ability to store all necessary data
 * in RAM through updating the user instance. When a permanent save is needed, the user instance
 * can be serialized and saved to a file.
 *
 * <p>Modifying the user instance is either done through the {@link User#getInstance()} method
 * and getting either the {@link ProjectRegistry} or {@link MonthlyBookkeepingRegistry} and
 * modifying them, or by using the {@link User#loadUser(User)} method to directly load a user
 * object.
 *
 * <p>The user class implements the Serializable interface. This is done so that the user instance
 * can be serialized and saved to a file.
 */
public class User implements Serializable {

  private static User instance;
  private final ProjectRegistry projectRegistry;
  private final MonthlyBookkeepingRegistry monthlyBookkeepingRegistry;

  private User(){
    projectRegistry = new ProjectRegistry();
    monthlyBookkeepingRegistry = new MonthlyBookkeepingRegistry();
  }

  /**
   * Get the user instance. If the instance is null, most likely because this is the first
   * time the method is called, a new instance is created.
   *
   * @return The user instance.
   */
  public static User getInstance() {
    if (instance == null) {
      instance = new User();
    }
    return instance;
  }
  
  /**
   * Loads a user instance. This method is mainly used for loading a user instance from a file as
   * modifying the user any other way can be achieved by using the {@link User#getInstance()}
   * method.
   *
   * @param user The user instance to load.
   */
  public void loadUser(User user) {
    instance = user;
  }
  
  /**
   * Returns the user's project registry. Modifying this object with the
   * {@link User#getInstance()} method will modify the user instance.
   *
   * @return The user's ProjectRegistry.
   */
  public ProjectRegistry getProjectRegistry() {
    return projectRegistry;
  }

  /**
   * Returns the user's monthly bookkeeping registry. Modifying this object with the
   * {@link User#getInstance()} method will modify the user instance.
   *
   * @return The user's MonthlyBookkeepingRegistry.
   */
  public MonthlyBookkeepingRegistry getMonthlyBookkeepingRegistry() {
    return monthlyBookkeepingRegistry;
  }
}
