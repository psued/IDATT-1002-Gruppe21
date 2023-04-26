package no.ntnu.idatt1002.app.bookkeeping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Transaction;

/**
 * The Budgeting class represents a bookkeeping system for tracking transactions related to the
 * budget of a project or other uses.
 *
 * <p>It implements the Bookkeeping interface and is Serializable for serialization
 * and deserialization.
 */
public class Budgeting implements Bookkeeping, Serializable {
  
  private final ArrayList<Income> incomeList;
  private final ArrayList<Expense> expenseList;

  /**
   * The constructor for this class.
   */
  public Budgeting() {
    incomeList = new ArrayList<>();
    expenseList = new ArrayList<>();
  }
  
  /**
   * Deep copy constructor for this class.
   *
   * @param budgeting the Budgeting object to be copied
   */
  public Budgeting(Budgeting budgeting) throws IllegalArgumentException {
    if (budgeting == null) {
      throw new IllegalArgumentException("budgeting cannot be null");
    }
    incomeList = new ArrayList<>(budgeting.getIncomeList());
    expenseList = new ArrayList<>(budgeting.getExpenseList());
  }
  
  /**
   * Method for adding a transaction to the income or expense list.
   *
   * @param transaction the transaction to be added
   */
  @Override
  public void addTransaction(Transaction transaction) throws IllegalArgumentException {
    if (transaction == null) {
      throw new IllegalArgumentException("transaction cannot be null");
    }
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
  public void updateTransaction(Transaction oldTransaction, Transaction newTransaction) throws
      IllegalArgumentException {
    if (oldTransaction == null || newTransaction == null) {
      throw new IllegalArgumentException("neither oldTransaction nor newTransaction can be null");
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
   * Removes a transaction from the income or expense list.
   *
   * @param transaction the transaction to be removed
   */
  @Override
  public void removeTransaction(Transaction transaction) throws IllegalArgumentException {
    if (transaction == null) {
      throw new IllegalArgumentException("transaction cannot be null");
    }
    if (transaction instanceof Income income) {
      incomeList.remove(income);
    } else if (transaction instanceof Expense expense) {
      expenseList.remove(expense);
    }
  }

  /**
   * Get a deep copy of the income list.
   *
   * @return a deep copy of the income list
   */
  public ArrayList<Income> getIncomeList() {
    ArrayList<Income> copy = new ArrayList<>();
    for (Income income : incomeList) {
      copy.add(new Income(income));
    }
    return copy;
  }

  /**
   * Get a deep copy of the expense list.
   *
   * @return a deep copy of the expense list
   */
  public ArrayList<Expense> getExpenseList() {
    ArrayList<Expense> copy = new ArrayList<>();
    for (Expense expense : expenseList) {
      copy.add(new Expense(expense));
    }
    return copy;
  }
  
  /**
   * Get a deep copy of the transaction list.
   *
   * @return a deep copy of the transaction list
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
   * @return the total amount of income
   */
  public double getTotalIncome() {
    return incomeList.stream().mapToDouble(Income::getAmount).sum();
  }

  /**
   * Get the sum of all expense amounts.
   *
   * @return the total expense
   */
  public double getTotalExpense() {
    return expenseList.stream().mapToDouble(Expense::getAmount).sum();
  }
  
  /**
   * Determines if the Budgeting object is equal to the specified object.
   *
   * @param obj the object to compare to
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Budgeting budgeting)) {
      return false;
    }
    
    return incomeList
      .equals(budgeting.getIncomeList()) && expenseList.equals(budgeting.getExpenseList());
  }
}