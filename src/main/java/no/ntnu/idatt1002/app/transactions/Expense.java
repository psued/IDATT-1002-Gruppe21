package no.ntnu.idatt1002.app.transactions;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <b>Expense class</b>
 *
 * <p>Class that represents an expense. Implements the Transaction interface and is Serializable
 * for serialization and deserialization.
 *
 * @see Transaction
 * @see Serializable
 */
public class Expense implements Transaction, Serializable {
  
  private LocalDate date;
  private String description;
  private String category;
  private double amount;
  
  /**
   * Creates an expense that is an instance of Transaction.
   *
   * @param date        The date of the Expense.
   * @param description The description of the Expense.
   * @param category    The category of the Expense.
   * @param amount      The amount of the Expense.
   * @throws IllegalArgumentException If description or category is null or blank
   *                                  or if amount is less than or equal to zero.
   */
  public Expense(String description, String category, double amount, LocalDate date)
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
   * Deep copy constructor. Creates a deep copy of the expense by copying all fields from the
   * given expense and setting them to the new expense.
   *
   * @param expense The expense to copy.
   * @throws IllegalArgumentException if expense is null or not an instance of Expense.
   */
  public Expense(Expense expense) throws IllegalArgumentException {
    if (expense == null) {
      throw new IllegalArgumentException("expense cannot be null");
    }
    
    this.description = expense.getDescription();
    this.category = expense.getCategory();
    this.amount = expense.getAmount();
    this.date = expense.getDate();
  }
  
  /**
   * Get the date of the Expense.
   *
   * @return date as LocalDate.
   */
  @Override
  public LocalDate getDate() {
    return date == null ? null :
        LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
  }
  
  /**
   * Set a new amount for the Expense.
   *
   * @param date The new date of the Expense.
   */
  @Override
  public void setDate(LocalDate date) {
    this.date = date;
  }
  
  /**
   * Get the description of the Expense.
   *
   * @return description as String.
   */
  @Override
  public String getDescription() {
    return description;
  }
  
  /**
   * Set a new description for the Expense.
   *
   * @param description The new description of the Expense.
   * @throws IllegalArgumentException if description is null or blank.
   */
  @Override
  public void setDescription(String description) throws IllegalArgumentException {
    if (description == null) {
      throw new IllegalArgumentException("description cannot be null");
    }
    if (description.isBlank()) {
      throw new IllegalArgumentException("newDescription cannot be blank");
    }
    
    this.description = description;
  }
  
  /**
   * Get the category of the Expense.
   *
   * @return category as String.
   */
  @Override
  public String getCategory() {
    return category;
  }
  
  /**
   * Set a new category for the Expense.
   *
   * @param category The new category of the Expense.
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
   * Get the amount of the Expense.
   *
   * @return amount as int.
   */
  @Override
  public double getAmount() {
    return amount;
  }
  
  /**
   * Set a new amount for the Expense.
   *
   * @param amount The new amount of the Expense.
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
   * Determines if the Expense object is equal to the specified object. Checks first if the
   * object is the same instance, then checks if the object is an instance of Expense and
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
    if (!(obj instanceof Expense expense)) {
      return false;
    }
    
    boolean equalsDate =
        expense.getDate() == null ? getDate() == null : expense.getDate().equals(getDate());
    
    return expense.getDescription().equals(description) && expense.getCategory().equals(category)
        && expense.getAmount() == amount && equalsDate;
  }
}
