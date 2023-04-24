package no.ntnu.idatt1002.app.registers;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.stream.Stream;
import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;

/**
 * The MonthlyBookkeeping is a register for all the bookkeeping in a month.
 *
 * <p>It separates the bookkeeping into two main categories and two subcategories. A transaction
 * is either meant for the accounting or the budgeting for a month. A transaction then either put
 * in the personal or work bookkeeping depending on what category it belongs to.
 */
public class MonthlyBookkeeping implements Serializable {
  
  private final Budgeting budgetingPersonal;
  private final Budgeting budgetingWork;
  private final Accounting accountingPersonal;
  private final Accounting accountingWork;
  private final YearMonth yearMonth;
  
  /**
   * Creates a MonthlyBookkeeping object with empty bookkeeping objects. The yearMonth must have
   * a valid value as it is used to identify the bookkeeping.
   *
   * @param yearMonth the year and month of the bookkeeping
   * @throws IllegalArgumentException if yearMonth is null
   */
  public MonthlyBookkeeping(YearMonth yearMonth) {
    budgetingPersonal = new Budgeting();
    budgetingWork = new Budgeting();
    accountingPersonal = new Accounting();
    accountingWork = new Accounting();
    
    if (yearMonth == null) {
      throw new IllegalArgumentException("YearMonth cannot be null");
    }
    this.yearMonth = yearMonth;
  }
  
  /**
   * Creates a deep copy of a MonthlyBookkeeping object.
   *
   * @param monthlyBookkeeping the MonthlyBookkeeping object to copy
   * @throws IllegalArgumentException if monthlyBookkeeping is null
   */
  public MonthlyBookkeeping(MonthlyBookkeeping monthlyBookkeeping) {
    if (monthlyBookkeeping == null) {
      throw new IllegalArgumentException("MonthlyBookkeeping cannot be null");
    }
    budgetingPersonal = new Budgeting((Budgeting) monthlyBookkeeping.getBookkeeping(false, true));
    budgetingWork = new Budgeting((Budgeting) monthlyBookkeeping.getBookkeeping(false, false));
    accountingPersonal = new Accounting((Accounting) monthlyBookkeeping.getBookkeeping(true, true));
    accountingWork = new Accounting((Accounting) monthlyBookkeeping.getBookkeeping(true, false));
    
    yearMonth = monthlyBookkeeping.getYearMonth();
  }
  
  /**
   * Get the chosen bookkeeping object based on the parameters.
   *
   * <p>Does not return a deep copy as this would make it impossible to modify the bookkeeping
   * objects.
   *
   * @param isAccounting true if accounting, false if budgeting
   * @param isPersonal true if personal, false if work
   * @return the chosen bookkeeping object
   */
  public Bookkeeping getBookkeeping(boolean isAccounting, boolean isPersonal) {
    return isAccounting ?
        (isPersonal ? accountingPersonal : accountingWork) :
        (isPersonal ? budgetingPersonal : budgetingWork);
  }
  
  /**
   * Get a deep copy bookkeeping object with all the transactions of both work and personal from
   * either accounting or budgeting.
   *
   * @param isAccounting true if accounting, false if budgeting
   * @return a deep copy bookkeeping object with all the transactions of both work and personal
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
    return budgetingPersonal.getTransactions().isEmpty() &&
        budgetingWork.getTransactions().isEmpty() &&
        accountingPersonal.getTransactions().isEmpty() &&
        accountingWork.getTransactions().isEmpty();
  }
  
  /**
   * Equals method for MonthlyBookkeeping. If the parameter is a MonthlyBookkeeping object but
   * does not have the same reference, it will check if the yearMonth of the two objects are the
   * same
   *
   * @param o the object to compare to
   * @return  true if the objects are the same or if the yearMonth of the two objects are the same,
   *          false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof MonthlyBookkeeping monthlyBookkeeping)) {
      return false;
    }
    return yearMonth.equals(monthlyBookkeeping.getYearMonth());
  }
}
