package no.ntnu.idatt1002.app.data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class that represents an income that implements the Transaction interface.

 * @author Lars Mikkel Nilsen, Trygve Jørgensen, Ingar Aasheim, Ari Maman
 * @since 10.03.2023
 */
public class Income implements Transaction, Serializable {

  String description;
  String category;
  double amount;
  LocalDate date;

  /**
   * Creates an income that is an instance of Transaction.
   * @param description The description of the Income.
   * @param category The category of the Income.
   * @param amount The amount of the Income.
   * @param date The date of the Income.
   * @throws IllegalArgumentException if description or category is null or blank or if amount is less than zero.
   */
  public Income(String description, String category, double amount, LocalDate date) throws IllegalArgumentException {
    if (description == null) throw new IllegalArgumentException("description cannot be null.");
    if (description.isBlank()) throw new IllegalArgumentException("description cannot be blank");
    if (category == null) throw new IllegalArgumentException("category cannot be null.");
    if (category.isBlank()) throw new IllegalArgumentException("category cannot be blank");
    if (amount < 0) throw new IllegalArgumentException("amount cannot be less than zero");

    this.description = description;
    this.category = category;
    this.amount = amount; 
    this.date = date;
  }

  /**
   * Method that gets the description of the Income.
   * @return description as String.
   */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * Method that gets the category of the Income.
   * @return category as String.
   */
  @Override
  public String getCategory() {
    return this.category;
  }

  /**
   * Method that gets the amount of the Income.
   * @return amount as int.
   */
  @Override
  public double getAmount() {
    return this.amount;
  }

  /**
   * Method that gets the date of the Income.
   * @return date as LocalDate.
   */
  @Override
  public LocalDate getDate() {
    return this.date;
  }

  /**
   * Method that sets a new description for the Income.
   * @param newDescription The new description of the Income.
   * @throws IllegalArgumentException if newDescription is null or blank.
   */
  @Override
  public void setDescription(String newDescription) throws IllegalArgumentException {
    if (newDescription == null) throw new IllegalArgumentException("newDescription cannot be null.");
    if (newDescription.isBlank()) throw new IllegalArgumentException("newDescription cannot be blank");
    this.description = newDescription;
  }

  /**
   * Method that sets a new category for the Income.
   * @param newCategory The new category of the Income.
   * @throws IllegalArgumentException if newCategory is null or blank.
   */
  @Override
  public void setCategory(String newCategory) throws IllegalArgumentException {
    if (newCategory == null) throw new IllegalArgumentException("newCategory cannot be null.");
    if (newCategory.isBlank()) throw new IllegalArgumentException("newCategory cannot be blank");
    this.category = newCategory;
  }

  /**
   * Method that sets a new amount for the Income.
   * @param newAmount The new amount of the Income.
   * @throws IllegalArgumentException if amount is less than zero.
   */
  @Override
  public void setAmount(double newAmount) throws IllegalArgumentException {
    if (newAmount < 0) throw new IllegalArgumentException("newAmount cannot be less than zero");
    this.amount = newAmount;
  }

  /**
   * Method that sets a new date for the Income.
   * @param newDate The new date of the Income.
   */
  @Override
  public void setDate(LocalDate newDate) {
    this.date = newDate;
  }
}
