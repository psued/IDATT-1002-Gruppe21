package no.ntnu.idatt1002.demo.data;

import java.util.Date;

/**
 * Interface for a transaction.
 * A transaction consists of the following attributes:
 * -Description
 * -Category
 * -Price
 * -Date

 *  @author Lars Mikkel Nilsen, Trygve Jørgensen, Ingar Aasheim, Ari Maman
 *  @since 10.02.2023
 */
public interface Transaction {

  /**
   * Method that gets the description of a transaction.
   * @return Description as String.
   */
  String getDescription();

  /**
   * Method that gets the category of a transaction.
   * @return Category as String.
   */
  String getCategory();

  /**
   * Method that gets the price of a transaction.
   * @return Price as double.
   */
  double getPrice();

  /**
   * Method that gets the date of a transaction.
   * @return Date as Date.
   */
  Date getDate();


  /**
   * Method that sets a new description for a transaction.
   * @throws IllegalArgumentException if newDescription is null or blank.
   */
  void setDescription(String newDescription) throws IllegalArgumentException;

  /**
   * Method that sets a new category for a transaction.
   * @throws IllegalArgumentException if newCategory is null or blank.
   */
  void setCategory(String newCategory) throws IllegalArgumentException;

  /**
   * Method that sets a new price for a transaction.
   * @throws IllegalArgumentException if price is less than zero.
   */
  void setPrice(double newPrice) throws IllegalArgumentException;

  /**
   * Method that sets a new date for a transaction.
   * @throws IllegalArgumentException if date is not valid.
   */
  void setDate(Date newDate) throws IllegalArgumentException;
}
