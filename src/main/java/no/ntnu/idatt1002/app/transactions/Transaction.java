package no.ntnu.idatt1002.app.transactions;

import java.time.LocalDate;

/**
 * Interface for a transaction.
 * A transaction consists of the following attributes:
 * -Description
 * -Category
 * -Amount
 * -Date
 */
public interface Transaction {

  /**
   * Method that gets the description of a transaction.
   *
   * @return Description as String.
   */
  String getDescription();

  /**
   * Method that gets the category of a transaction.
   *
   * @return Category as String.
   */
  String getCategory();

  /**
   * Method that gets the amount of a transaction.
   *
   * @return Amount as double.
   */
  double getAmount();

  /**
   * Method that gets the date of a transaction.
   *
   * @return Date as LocalDate.
   */
  LocalDate getDate();

  /**
   * Method that sets a new description for a transaction.
   *
   * @param newDescription The new description of the transaction.
   * @throws IllegalArgumentException if newDescription is null or blank.
   */
  void setDescription(String newDescription) throws IllegalArgumentException;

  /**
   * Method that sets a new category for a transaction.
   *
   * @param newCategory The new category of the transaction.
   * @throws IllegalArgumentException if newCategory is null or blank.
   */
  void setCategory(String newCategory) throws IllegalArgumentException;

  /**
   * Method that sets a new amount for a transaction.
   *
   * @param newAmount The new amount of the transaction.
   * @throws IllegalArgumentException if amount is less than zero.
   */
  void setAmount(double newAmount) throws IllegalArgumentException;

  /**
   * Method that sets a new date for a transaction.
   *
   * @param newDate The new date of the transaction.
   * @throws IllegalArgumentException if date is not valid.
   */
  void setDate(LocalDate newDate);

  /**
   * Determines if the Transaction object is equal to the specified object.
   *
   * @param obj The object to compare with.
   * @return True if the transactions are equal, false otherwise.
   */
  boolean equals(Object obj);

}
