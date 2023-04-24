package no.ntnu.idatt1002.app.bookkeeping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Transaction;

/**
 * The Accounting class represents a bookkeeping system for tracking income and
 * expenses for the accounting of a project.
 *
 * <p>It implements the Bookkeeping interface and is Serializable for serialization
 * and deserialization.
 */
public class Accounting implements Bookkeeping, Serializable {

  private final ArrayList<Income> incomeList;
  private final ArrayList<Expense> expenseList;

  /**
   * Creates an Accounting object with empty income and expense lists.
   */
  public Accounting() {
    incomeList = new ArrayList<>();
    expenseList = new ArrayList<>();
  }
  
  /**
   * Creates a deep copy of an Accounting object.
   *
   * @param accounting the Accounting object to copy.
   */
  public Accounting(Accounting accounting) {
    incomeList = new ArrayList<>(accounting.getIncomeList());
    expenseList = new ArrayList<>(accounting.getExpenseList());
  }
  
  /**
   * Adds a Transaction object to the income or expense list.
   *
   * @param transaction the Transaction object to add.
   */
  @Override
  public void addTransaction(Transaction transaction) {
    if (transaction instanceof Income income) {
      incomeList.add(income);
    } else if (transaction instanceof Expense expense) {
      expenseList.add(expense);
    }
  }
  
  /**
   * Updates a Transaction object. Handles weather all possible combinations of different types
   * the old and new Transaction objects are.
   *
   * @param oldTransaction the old Transaction object to update from.
   * @param newTransaction the new Transaction object to update to.
   */
  @Override
  public void updateTransaction(Transaction oldTransaction, Transaction newTransaction) {
    if (oldTransaction == null || newTransaction == null) {
      throw new IllegalArgumentException("All arguments must be non-null");
    }
    
    if (newTransaction instanceof Income newIncome) {
      if (oldTransaction instanceof Income oldIncome) {
        incomeList.set(incomeList.indexOf(oldIncome), newIncome);
      } else if (oldTransaction instanceof Expense oldExpense) {
        incomeList.add(newIncome);
        expenseList.remove(oldExpense);
      }
    } else if (newTransaction instanceof Expense expense) {
      if (oldTransaction instanceof Income income) {
        expenseList.add(expense);
        incomeList.remove(income);
      } else if (oldTransaction instanceof Expense oldExpense) {
        expenseList.set(expenseList.indexOf(oldExpense), expense);
      }
    }
  }
  
  
  /**
   * Removes a Transaction object from the income or expense list.
   *
   * @param transaction the Transaction object to remove.
   */
  @Override
  public void removeTransaction(Transaction transaction) {
    if (transaction instanceof Income income) {
      incomeList.remove(income);
    } else if (transaction instanceof Expense expense) {
      expenseList.remove(expense);
    }
  }

  /**
   * Get a deep copy of the list of Income objects.
   *
   * @return a list of Income objects.
   */
  public ArrayList<Income> getIncomeList() {
    ArrayList<Income> copy = new ArrayList<>();
    for (Income income : incomeList) {
      copy.add(new Income(income));
    }
    return copy;
  }

  /**
   * Get a deep copy of the list of Expense objects.
   *
   * @return a list of Expense objects.
   */
  public ArrayList<Expense> getExpenseList() {
    ArrayList<Expense> copy = new ArrayList<>();
    for (Expense expense : expenseList) {
      copy.add(new Expense(expense));
    }
    return copy;
  }
  
  /**
   * Get a deep copy of the list of Transaction objects.
   *
   * @return a list of all transactions
   */
  @Override
  public List<Transaction> getTransactions() {
    List<Transaction> copy = new ArrayList<>();
    copy.addAll(getIncomeList());
    copy.addAll(getExpenseList());
    return copy;
  }
  
  /**
   * Get the sum of all income amounts.
   *
   * @return the total amount of income.
   */
  public double getTotalIncome() {
    return incomeList.stream().mapToDouble(Income::getAmount).sum();
  }

  /**
   * Get the sum of all expense amounts.
   *
   * @return the total amount of expenses.
   */
  public double getTotalExpense() {
    return expenseList.stream().mapToDouble(Expense::getAmount).sum();
  }

  @Override
  public void reset() {
    incomeList.clear();
    expenseList.clear();
  }

  /**
   * Determines if the Accounting object is equal to the specified object.
   *
   * @param obj the object to compare to.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Accounting accounting)) {
      return false;
    }
    
    return incomeList
        .equals(accounting.getIncomeList()) && expenseList.equals(accounting.getExpenseList());
  }
}