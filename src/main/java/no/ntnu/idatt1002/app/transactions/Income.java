package no.ntnu.idatt1002.app.transactions;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <b>Income class</b>
 *
 * <p>Class that represents an income. Implements the Transaction interface and is Serializable
 * for serialization and deserialization.
 *
 * @see Transaction
 * @see Serializable
 */
public class Income implements Transaction, Serializable {
  
  private LocalDate date;
  private String description;
  private String category;
  private double amount;
  
  /**
   * Creates an income that is an instance of Transaction.
   *
   * @param date        The date of the Income.
   * @param description The description of the Income.
   * @param category    The category of the Income.
   * @param amount      The amount of the Income.
   * @throws IllegalArgumentException If description or category is null or blank
   *                                  or if amount is less than zero.
   */
  public Income(String description, String category, double amount, LocalDate date)
      throws IllegalArgumentException {
    if (description == null) {
      throw new IllegalArgumentException("description cannot be null");
    }
    if (description.isBlank()) {
      throw new IllegalArgumentException("description cannot be blank");
    }
    if (category == null) {
      throw new IllegalArgumentException("category cannot be null");
    }
    if (category.isBlank()) {
      throw new IllegalArgumentException("category cannot be blank");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("amount cannot be less than or equal to zero");
    }
    
    this.description = description;
    this.category = category;
    this.amount = amount;
    this.date = date;
  }
  
  /**
   * Deep copy constructor. Creates a deep copy of the income by copying all fields from the
   * given income and setting them to the new income.
   *
   * @param income The income to copy.
   * @throws IllegalArgumentException if income is null or not an instance of Income.
   */
  public Income(Income income) throws IllegalArgumentException {
    if (income == null) {
      throw new IllegalArgumentException("income cannot be null");
    }
    this.date = income.getDate();
    this.description = income.getDescription();
    this.category = income.getCategory();
    this.amount = income.getAmount();
  }
  
  /**
   * Gets the date of the Income.
   *
   * @return date as LocalDate.
   */
  @Override
  public LocalDate getDate() {
    return date;
  }
  
  /**
   * Sets a new date for the Income.
   *
   * @param date The new date of the Income.
   */
  @Override
  public void setDate(LocalDate date) {
    this.date = date;
  }
  
  /**
   * Gets the description of the Income.
   *
   * @return description as String.
   */
  @Override
  public String getDescription() {
    return description;
  }
  
  /**
   * Sets a new description for the Income.
   *
   * @param description The new description of the Income.
   * @throws IllegalArgumentException if description is null or blank.
   */
  @Override
  public void setDescription(String description) throws IllegalArgumentException {
    if (description == null) {
      throw new IllegalArgumentException("description cannot be null");
    }
    if (description.isBlank()) {
      throw new IllegalArgumentException("description cannot be blank");
    }
    this.description = description;
  }
  
  /**
   * Gets the category of the Income.
   *
   * @return category as String.
   */
  @Override
  public String getCategory() {
    return category;
  }
  
  /**
   * Sets a new category for the Income.
   *
   * @param category The new category of the Income.
   * @throws IllegalArgumentException if category is null or blank.
   */
  @Override
  public void setCategory(String category) throws IllegalArgumentException {
    if (category == null) {
      throw new IllegalArgumentException("category cannot be null");
    }
    if (category.isBlank()) {
      throw new IllegalArgumentException("category cannot be blank");
    }
    this.category = category;
  }
  
  /**
   * Gets the amount of the Income.
   *
   * @return amount as int.
   */
  @Override
  public double getAmount() {
    return amount;
  }
  
  /**
   * Sets a new amount for the Income.
   *
   * @param amount The new amount of the Income.
   * @throws IllegalArgumentException if amount is less than zero.
   */
  @Override
  public void setAmount(double amount) throws IllegalArgumentException {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount cannot be less than or equal to zero");
    }
    this.amount = amount;
  }
  
  /**
   * Determines if the Income object is equal to the specified object. Checks if the
   * object is an instance, then checks if the object is an instance of Income and
   * finally checks if all fields are equal.
   *
   * @param obj The object to compare to.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Income income)) {
      return false;
    }
    
    boolean isEqual = income.getDate() == null ? date == null : income.getDate().equals(date);
    
    return income.getDescription().equals(description) && income.getCategory().equals(category)
        && income.getAmount() == amount && isEqual;
  }
}
