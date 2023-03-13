package no.ntnu.idatt1002.app.registry;

import java.time.LocalDateTime;
import java.util.HashMap;
import no.ntnu.idatt1002.app.data.Bookkeeping;


/**
 * A registry is used to keep track of all Bookkeeping objects. A registry will either represent
 * all projects or all monthly transactions records.
 */
public interface Registry {

  /**
   * Add a Bookkeeping object to the registry.
   *
   * @param key       the date and time corresponding to the object
   * @param bookkeeping the object to be added to the registry
   * @throws IllegalArgumentException if the object is null
   */
  void addToRegistry(LocalDateTime key, Bookkeeping bookkeeping) throws IllegalArgumentException;
  
  /**
   * Get a Bookkeeping object from the registry.
   *
   * @param key the date and time corresponding to the object
   * @return the bookkeeping object from the registry
   */
  Bookkeeping getFromRegistry(LocalDateTime key);
  
  
  /**
   * Gets the entire registry of bookkeeping objects.
   *
   * @return the entire registry of bookkeeping objects
   */
  HashMap<LocalDateTime, Bookkeeping> getRegistry();
}
