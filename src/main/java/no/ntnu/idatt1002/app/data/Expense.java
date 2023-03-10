package no.ntnu.idatt1002.app.data;

import java.time.LocalDate;

/**
 * Class that represents an expense that implements the Transaction interface.

 * @author Lars Mikkel Nilsen, Trygve Jørgensen, Ingar Aasheim, Ari Maman
 * @since 10.03.2023
 */
public class Expense implements Transaction {

  String description;
  String category;
  double price;
  LocalDate date;

  /**
   * Creates an expense that is an instance of Transaction.
   * @param description The description of the Expense.
   * @param category The category of the Expense.
   * @param price The price of the Expense.
   * @param date The date of the Expense.
   * @throws IllegalArgumentException if description or category is null or blank or if price is less than zero.
   */
  public Expense(String description, String category, double price, LocalDate date) throws IllegalArgumentException {
    if (description == null) throw new IllegalArgumentException("description cannot be null.");
    if (description.isBlank()) throw new IllegalArgumentException("description cannot be blank");
    if (category == null) throw new IllegalArgumentException("category cannot be null.");
    if (category.isBlank()) throw new IllegalArgumentException("category cannot be blank");
    if (price < 0) throw new IllegalArgumentException("price cannot be less than zero");

    this.description = description;
    this.category = category;
    this.price = price;
    this.date = date;
  }

  /**
   * Method that gets the description of the Expense.
   * @return description as String.
   */
  @Override
  public String getDescription() {
    return this.description;
  }

  /**
   * Method that gets the category of the Expense.
   * @return category as String.
   */
  @Override
  public String getCategory() {
    return this.category;
  }

  /**
   * Method that gets the price of the Expense.
   * @return price as int.
   */
  @Override
  public double getPrice() {
    return this.price;
  }

  /**
   * Method that gets the date of the Expense.
   * @return date as LocalDate.
   */
  @Override
  public LocalDate getDate() {
    return this.date;
  }

  /**
   * Method that sets a new description for the Expense.
   * @param newDescription The new description of the Expense.
   * @throws IllegalArgumentException if newDescription is null or blank.
   */
  @Override
  public void setDescription(String newDescription) throws IllegalArgumentException {
    if (newDescription == null) throw new IllegalArgumentException("newDescription cannot be null.");
    if (newDescription.isBlank()) throw new IllegalArgumentException("newDescription cannot be blank");
    this.description = newDescription;
  }

  /**
   * Method that sets a new category for the Expense.
   * @param newCategory The new category of the Expense.
   * @throws IllegalArgumentException if newCategory is null or blank.
   */
  @Override
  public void setCategory(String newCategory) throws IllegalArgumentException {
    if (newCategory == null) throw new IllegalArgumentException("newCategory cannot be null.");
    if (newCategory.isBlank()) throw new IllegalArgumentException("newCategory cannot be blank");
    this.category = newCategory;
  }

  /**
   * Method that sets a new price for the Expense.
   * @param newPrice The new price of the Expense.
   * @throws IllegalArgumentException if price is less than zero.
   */
  @Override
  public void setPrice(double newPrice) throws IllegalArgumentException {
    if (newPrice < 0) throw new IllegalArgumentException("newPrice cannot be less than zero");
    this.price = newPrice;
  }

  /**
   * Method that sets a new date for the Expense.
   * @param newDate The new date of the Expense.
   */
  @Override
  public void setDate(LocalDate newDate) {
    this.date = newDate;
  }
}
