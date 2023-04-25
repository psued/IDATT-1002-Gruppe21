package no.ntnu.idatt1002.app.transactions;

import java.time.LocalDate;

/**
 * <b>Transaction interface</b>
 *
 * <p>A transaction can either be an expense or an income.
 * <p>It has a date, a description, a category and an amount.
 */
public interface Transaction {
  /**
   * Get the date of a transaction, can be null.
   *
   * @return Date as LocalDate.
   */
  LocalDate getDate();

  /**
   * Get the description of a transaction.
   *
   * @return Description as String.
   */
  String getDescription();

  /**
   * Get the category of a transaction.
   *
   * @return Category as String.
   */
  String getCategory();

  /**
   * Get the amount of a transaction. Amount is always larger than zero.
   *
   * @return Amount as double.
   */
  double getAmount();
  
  /**
   * Set the date of a transaction.
   *
   * @param newDate The new date of the transaction.
   */
  void setDate(LocalDate newDate);
  
  /**
   * Set the description of a transaction.
   *
   * @param newDescription The new description of the transaction.
   * @throws IllegalArgumentException if newDescription is null or blank.
   */
  void setDescription(String newDescription) throws IllegalArgumentException;

  /**
   * Set the category of a transaction.
   *
   * @param newCategory The new category of the transaction.
   * @throws IllegalArgumentException if newCategory is null or blank.
   */
  void setCategory(String newCategory) throws IllegalArgumentException;

  /**
   * Set the amount of a transaction.
   *
   * @param newAmount The new amount of the transaction.
   * @throws IllegalArgumentException if amount is less than or equal to zero.
   */
  void setAmount(double newAmount) throws IllegalArgumentException;


  /**
   * Determines if the Transaction object is equal to the specified object.
   *
   * @param obj The object to compare with.
   * @return True if the transactions are equal, false otherwise.
   */
  boolean equals(Object obj);

}
