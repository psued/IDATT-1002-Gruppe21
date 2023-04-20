package no.ntnu.idatt1002.app.bookkeeping;

import java.util.List;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Transaction;

/**
 * Bookkeeping interface is used to keep track of all the income and expenses for either
 * budgeting for accounting.
 */
public interface Bookkeeping {
  
  /**
   * Method for adding an income to the income list.
   *
   * @param income the income to be added
   */
  void addIncome(Income income);
  
  /**
   * Method for adding an expense to the expense list.
   *
   * @param expense the expense to be added
   */
  void addExpense(Expense expense);
  
  /**
   * Method for adding a transaction to the transaction list.
   *
   * @param transaction the transaction to be added
   */
  void addTransaction(Transaction transaction);
  
  /**
   * Remove a transaction from the either the income or expense list.
   *
   * @param transaction the transaction to be removed
   */
  void removeTransaction(Transaction transaction);
  
  /**
   * Method for getting the income list.
   *
   * @return the income list
   */
  List<Income> getIncomeList();
  
  /**
   * Method for getting the expense list.
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
   * Method for getting the total income.
   *
   * @return the total income amount
   */
  double getTotalIncome();
  
  /**
   * Method for getting the total expense.
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
