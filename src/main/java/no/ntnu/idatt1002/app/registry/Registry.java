package no.ntnu.idatt1002.app.registry;

import java.time.LocalDateTime;
import java.util.HashMap;


/**
 * The registry interface tracks all data that is created in the application. Different
 * registries will track different types of data.
 */
public interface Registry {
  /**
   * Add an object to the registry. The dateTime key chosen for the object can be a reference to
   * a specific time relative to the object, or a time when the object was created. This is
   * decided in the registry class.
   *
   * @param key  the date and time corresponding to the object
   * @param object    the object to be added
   */
  void addToRegistry(LocalDateTime key, Object object);
  
  /**
   * Get an object from the registry.
   *
   * @param key the date and time corresponding to the object
   * @return the specific object from the registry
   */
  Object getFromRegistry(LocalDateTime key);
  
  /**
   * Gets the entire registry.
   *
   * @return the entire registry
   */
  HashMap<LocalDateTime, Object> getRegistry();
}
