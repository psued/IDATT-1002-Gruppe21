package no.ntnu.idatt1002.app.transactions;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <b>Income class</b>
 *
 * <p>Class that represents an income. Implements the Transaction interface and is Serializable
 * for serialization and deserialization.
 */
public class Income implements Transaction, Serializable {

  private LocalDate date;
  private String description;
  private String category;
  private double amount;

  /**
   * Creates an income that is an instance of Transaction.
   *
   * @param date                      The date of the Income.
   * @param description               The description of the Income.
   * @param category                  The category of the Income.
   * @param amount                    The amount of the Income.
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
   * Deep copy constructor.
   *
   * @param income The income to copy.
   * @throws IllegalArgumentException if income is null or not an instance of Income.
   */
  public Income(Income income) throws IllegalArgumentException {
    if (income == null) {
      throw new IllegalArgumentException("income cannot be null");
    }
    if (!(income instanceof Income)) {
      throw new IllegalArgumentException("income must be an instance of Income");
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
   * Gets the description of the Income.
   *
   * @return description as String.
   */
  @Override
  public String getDescription() {
    return description;
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
   * Gets the amount of the Income.
   *
   * @return amount as int.
   */
  @Override
  public double getAmount() {
    return amount;
  }
  
  /**
   * Sets a new date for the Income.
   *
   * @param newDate The new date of the Income.
   */
  @Override public void setDate(LocalDate newDate) {
    date = newDate;
  }

  /**
   * Gets the type of the Income.
   *
   * @param newDescription The new description of the Income.
   * @throws IllegalArgumentException if newDescription is null or blank.
   */
  @Override
  public void setDescription(String newDescription) throws IllegalArgumentException {
    if (newDescription == null) {
      throw new IllegalArgumentException("newDescription cannot be null");
    }
    if (newDescription.isBlank()) {
      throw new IllegalArgumentException("newDescription cannot be blank");
    }
    description = newDescription;
  }

  /**
   * Sets a new category for the Income.
   *
   * @param newCategory The new category of the Income.
   * @throws IllegalArgumentException if newCategory is null or blank.
   */
  @Override
  public void setCategory(String newCategory) throws IllegalArgumentException {
    if (newCategory == null) {
      throw new IllegalArgumentException("newCategory cannot be null");
    }
    if (newCategory.isBlank()) {
      throw new IllegalArgumentException("newCategory cannot be blank");
    }
    category = newCategory;
  }

  /**
   * Sets a new amount for the Income.
   *
   * @param newAmount The new amount of the Income.
   * @throws IllegalArgumentException if amount is less than zero.
   */
  @Override
  public void setAmount(double newAmount) throws IllegalArgumentException {
    if (newAmount <= 0) {
      throw new IllegalArgumentException("newAmount cannot be less than or equal to zero");
    }
    amount = newAmount;
  }
  
  /**
   * Determines if the Income object is equal to the specified object.
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
    
    boolean isEqual = income.getDate() == null ? date == null
        : income.getDate().equals(date);
    
    return income.getDescription().equals(description)
        && income.getCategory().equals(category)
        && income.getAmount() == amount && isEqual;
  }
}
