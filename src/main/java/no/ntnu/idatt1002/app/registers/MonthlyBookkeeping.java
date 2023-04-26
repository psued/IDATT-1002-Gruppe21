package no.ntnu.idatt1002.app.registers;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.stream.Stream;
import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;

/**
 * The MonthlyBookkeeping class represents a bookkeeping system for tracking transactions related
 * to an entire month.
 *
 * <p>A monthly bookkeeping object uses the {@link YearMonth YearMonth} class aa an identifier.
 *
 * <p>It separates the bookkeeping into two main categories and two subcategories resulting in
 * four bookkeeping objects. The main categories are accounting and budgeting. Then the
 * subcategories are personal and work.
 *
 * <p>It implements the Serializable interface for serialization and deserialization of object.
 *
 * @see YearMonth
 * @see Bookkeeping
 * @see Serializable
 */
public class MonthlyBookkeeping implements Serializable {
  
  private final Budgeting budgetingPersonal;
  private final Budgeting budgetingWork;
  private final Accounting accountingPersonal;
  private final Accounting accountingWork;
  private final YearMonth yearMonth;
  
  /**
   * Creates a MonthlyBookkeeping object with empty bookkeeping objects.
   *
   * @param yearMonth the year and month of the bookkeeping
   * @throws IllegalArgumentException if yearMonth is null
   */
  public MonthlyBookkeeping(YearMonth yearMonth) throws IllegalArgumentException {
    if (yearMonth == null) {
      throw new IllegalArgumentException("YearMonth cannot be null");
    }
    
    this.yearMonth = yearMonth;
    budgetingPersonal = new Budgeting();
    budgetingWork = new Budgeting();
    accountingPersonal = new Accounting();
    accountingWork = new Accounting();
  }
  
  /**
   * Get the chosen bookkeeping object based on the main and subcategory.
   *
   * @param isAccounting true if accounting, false if budgeting
   * @param isPersonal   true if personal, false if work
   * @return the chosen bookkeeping object
   */
  public Bookkeeping getBookkeeping(boolean isAccounting, boolean isPersonal) {
    return isAccounting ? (isPersonal ? accountingPersonal : accountingWork) :
        (isPersonal ? budgetingPersonal : budgetingWork);
  }
  
  /**
   * Get a new bookkeeping object with all the transactions from the two chosen bookkeeping
   * objects combined into a single bookkeeping object. Also, deep copies the data to prevent
   * unwanted changes to the original data.
   *
   * @param isAccounting true if accounting, false if budgeting
   */
  public Bookkeeping getTotalBookkeeping(boolean isAccounting) {
    if (isAccounting) {
      Accounting personalCopy = new Accounting(accountingPersonal);
      Accounting workCopy = new Accounting(accountingWork);
      
      Accounting totalCopy = new Accounting();
      Stream.concat(personalCopy.getTransactions().stream(), workCopy.getTransactions().stream())
          .forEach(totalCopy::addTransaction);
      
      return totalCopy;
    } else {
      Budgeting personalCopy = new Budgeting(budgetingPersonal);
      Budgeting workCopy = new Budgeting(budgetingWork);
      
      Budgeting totalCopy = new Budgeting();
      Stream.concat(personalCopy.getTransactions().stream(), workCopy.getTransactions().stream())
          .forEach(totalCopy::addTransaction);
      
      return totalCopy;
    }
  }
  
  /**
   * Get a deep copy of the yearMonth of the bookkeeping.
   *
   * @return a deep copy of the yearMonth of the bookkeeping
   */
  public YearMonth getYearMonth() {
    return YearMonth.from(yearMonth);
  }
  
  /**
   * Check if this month has any transactions by checking all the transactions in every bookkeeping
   * object.
   *
   * @return true if there are no transactions in any bookkeeping object, false otherwise
   */
  public boolean isEmpty() {
    return budgetingPersonal.getTransactions().isEmpty()
        && budgetingWork.getTransactions().isEmpty()
        && accountingPersonal.getTransactions().isEmpty()
        && accountingWork.getTransactions().isEmpty();
  }
  
  /**
   * Determines if two MonthlyBookkeeping objects are the same by comparing the yearMonth of the
   * objects, as the yearMonth is utilized as an identifier for the object.
   *
   * @param obj the object to compare to
   * @return true if the objects have the same yearMonth, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MonthlyBookkeeping monthlyBookkeeping)) {
      return false;
    }
    return yearMonth.equals(monthlyBookkeeping.getYearMonth());
  }
}
