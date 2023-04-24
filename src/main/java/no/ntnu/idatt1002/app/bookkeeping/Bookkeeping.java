package no.ntnu.idatt1002.app.bookkeeping;

import java.util.List;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Transaction;

/**
 * <b>Bookkeeping interface</b>
 *
 * <p>Bookkeeping interface is used for adding, getting and removing transactions in a list.
 */
public interface Bookkeeping {
  /**
   * Add a transaction to either the income or expense list.
   *
   * @param transaction the transaction to be added
   */
  void addTransaction(Transaction transaction);
  
  /**
   * Remove a transaction from either the income or expense list.
   *
   * @param transaction the transaction to be removed
   */
  void removeTransaction(Transaction transaction);
  
  /**
   * Get the income list.
   *
   * @return the income list
   */
  List<Income> getIncomeList();
  
  /**
   * Get the expense list.
   *
   * @return the expense list
   */
  List<Expense> getExpenseList();
  
  /**
   * Get all transactions.
   *
   * @return all transactions
   */
  List<Transaction> getTransactions();
  
  /**
   * Get the total income amount.
   *
   * @return the total income amount
   */
  double getTotalIncome();
  
  /**
   * Get the total expense amount.
   *
   * @return the total expense amount
   */
  double getTotalExpense();

  void reset();

  /**
   * Determines if the Bookkeeping object is equal to the specified object.
   *
   * @param obj the object to compare to
   * @return true if the objects are equal, false otherwise
   */
  boolean equals(Object obj);
}
