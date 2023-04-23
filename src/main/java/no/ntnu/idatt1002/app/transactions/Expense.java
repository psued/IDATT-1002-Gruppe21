package no.ntnu.idatt1002.app.transactions;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <b>Expense class</b>
 *
 * <p>Class that represents an expense. Implements the Transaction interface and is Serializable
 * for serialization and deserialization.
 */
public class Expense implements Transaction, Serializable {

  private LocalDate date;
  private String description;
  private String category;
  private double amount;
  
  /**
   * Creates an expense that is an instance of Transaction.
   *
   * @param date                      The date of the Expense.
   * @param description               The description of the Expense.
   * @param category                  The category of the Expense.
   * @param amount                    The amount of the Expense.
   * @throws IllegalArgumentException If description or category is null or blank
   *                                  or if amount is less than zero.
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
   * Deep copy constructor.
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
   * Get a deep copy of the date.
   *
   * @return date as LocalDate.
   */
  @Override
  public LocalDate getDate() {
    return date == null ? null : LocalDate.of(date.getYear(), date.getMonth(),
        date.getDayOfMonth());
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
   * Get the category of the Expense.
   *
   * @return category as String.
   */
  @Override
  public String getCategory() {
    return category;
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
   * @param newDate The new date of the Expense.
   */
  @Override
  public void setDate(LocalDate newDate) {
    date = newDate;
  }

  /**
   * Set a new description for the Expense.
   *
   * @param newDescription The new description of the Expense.
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
   * Set a new category for the Expense.
   *
   * @param newCategory The new category of the Expense.
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
   * Set a new amount for the Expense.
   *
   * @param newAmount The new amount of the Expense.
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
   * Determines if the Expense object is equal to the specified object.
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
    
    boolean equalsDate = expense.getDate() == null ? getDate() == null :
        expense.getDate().equals(getDate());
  
    return expense.getDescription().equals(description)
        && expense.getCategory().equals(category)
        && expense.getAmount() == amount && equalsDate;
  }
}
