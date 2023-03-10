package no.ntnu.idatt1002.app.registry;

import java.util.HashMap;

/**
 * The registry interface tracks all data that is created in the application. Different
 * registries will track different types of data.
 */
public interface Registry {
  
  /**
   * Add an object to the registry.
   *
   * @param key    an immutable attribute of the object
   * @param object the object to be added
   */
  void addToRegistry(Object key, Object object);
  
  /**
   * Get an object from the registry.
   *
   * @param key the key of the object
   * @return the specific object from the registry
   */
  Object getFromRegistry(Object key);
  
  /**
   * Gets the entire registry.
   *
   * @return the entire registry
   */
  HashMap<Object, Object> getRegistry();
}
