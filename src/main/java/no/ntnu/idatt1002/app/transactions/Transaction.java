package no.ntnu.idatt1002.app.transactions;

import java.time.LocalDate;

/**
 * <b>Transaction interface</b>
 *
 * <p>A transaction can either be an expense or an income.
 *
 * <p>It has a date, a description, a category and an amount.
 *
 * <p>Both the description and category must not be null or blank and the amount must be larger
 * than zero.
 */
public interface Transaction {
  /**
   * Get the date of a transaction, can be null.
   *
   * @return Date as LocalDate.
   */
  LocalDate getDate();
  
  /**
   * Set the date of a transaction.
   *
   * @param date The new date of the transaction.
   */
  void setDate(LocalDate date);
  
  /**
   * Get the description of a transaction.
   *
   * @return Description as String.
   */
  String getDescription();
  
  /**
   * Set the description of a transaction.
   *
   * @param description The new description of the transaction.
   * @throws IllegalArgumentException if description is null or blank.
   */
  void setDescription(String description) throws IllegalArgumentException;
  
  /**
   * Get the category of a transaction.
   *
   * @return Category as String.
   */
  String getCategory();
  
  /**
   * Set the category of a transaction.
   *
   * @param category The new category of the transaction.
   * @throws IllegalArgumentException if category is null or blank.
   */
  void setCategory(String category) throws IllegalArgumentException;
  
  /**
   * Get the amount of a transaction. Amount is always larger than zero.
   *
   * @return Amount as double.
   */
  double getAmount();
  
  /**
   * Set the amount of a transaction.
   *
   * @param amount The new amount of the transaction.
   * @throws IllegalArgumentException if amount is less than or equal to zero.
   */
  void setAmount(double amount) throws IllegalArgumentException;
  
  
  /**
   * Determines if the Transaction object is equal to the specified object.
   *
   * @param obj The object to compare with.
   * @return True if the transactions are equal, false otherwise.
   */
  boolean equals(Object obj);
  
}
