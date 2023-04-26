package no.ntnu.idatt1002.app.bookkeeping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1002.app.registers.Project;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Transaction;

/**
 * The Accounting class represents a {@link Bookkeeping bookeeping} system for tracking
 * transactions related to the budget of a {@link Project project} or other uses. Has separate
 * lists for income and expenses.
 *
 * <p>It implements the Bookkeeping interface and uses the Serializable interface for
 * serialization and deserialization of object.
 *
 * @see Bookkeeping
 * @see Serializable
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
   * Deep copy constructor for this class. Copies the income and expense lists by recursively
   * deep copying all {@link Transaction transactions} and putting them in new lists.
   *
   * @param accounting the Accounting object to be copied
   * @see #getIncomeList()
   * @see #getExpenseList()
   * @see Income#Income(Income)
   * @see Expense#Expense(Expense)
   */
  public Accounting(Accounting accounting) throws IllegalArgumentException {
    if (accounting == null) {
      throw new IllegalArgumentException("accounting cannot be null");
    }
    incomeList = new ArrayList<>(accounting.getIncomeList());
    expenseList = new ArrayList<>(accounting.getExpenseList());
  }
  
  /**
   * Adds a {@link Transaction transaction} object to the income or expense list depending on
   * whether the {@link Transaction transaction} object is an instance of {@link Income Income}
   * or {@link Expense Expense}.
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
   * Updates a {@link Transaction transaction} object in the income or expense list depending on
   * whether the {@link Transaction transaction} object is an instance of {@link Income Income}
   * or {@link Expense Expense}.
   *
   * @param oldTransaction the old Transaction object to update from.
   * @param newTransaction the new Transaction object to update to.
   * @throws IllegalArgumentException if any of the arguments are null.
   */
  @Override
  public void updateTransaction(Transaction oldTransaction, Transaction newTransaction)
      throws IllegalArgumentException {
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
   * Removes a {@link Transaction transaction} object from the income or expense list depending on
   * whether the {@link Transaction transaction} object is an instance of {@link Income Income}
   * or {@link Expense Expense}.
   *
   * @param transaction the transaction to be removed
   * @throws IllegalArgumentException if the argument is null.
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
   * Get a deep copy of the {@link Income income} list. This is done by recursively deep copying
   * every income object in the list and putting them in a new list.
   *
   * @return a deep copy of the income list
   * @see Income#Income(Income)
   */
  public ArrayList<Income> getIncomeList() {
    ArrayList<Income> copy = new ArrayList<>();
    for (Income income : incomeList) {
      copy.add(new Income(income));
    }
    return copy;
  }
  
  /**
   * Get a deep copy of the {@link Expense expense} list. This is done by recursively deep copying
   * every expense object in the list and putting them in a new list.
   *
   * @return a deep copy of the expense list
   * @see Expense#Expense(Expense)
   */
  public ArrayList<Expense> getExpenseList() {
    ArrayList<Expense> copy = new ArrayList<>();
    for (Expense expense : expenseList) {
      copy.add(new Expense(expense));
    }
    return copy;
  }
  
  /**
   * Get a combined list of all {@link Transaction transactions} in the income and expense lists.
   *
   * @return a combined list of all transactions.
   */
  @Override
  public List<Transaction> getTransactions() {
    List<Transaction> copy = new ArrayList<>();
    copy.addAll(getIncomeList());
    copy.addAll(getExpenseList());
    return copy;
  }
  
  /**
   * Get the sum of all {@link Income income} amounts.
   *
   * @return the total amount of income.
   */
  public double getTotalIncome() {
    return incomeList.stream().mapToDouble(Income::getAmount).sum();
  }
  
  /**
   * Get the sum of all {@link Expense expense} amounts.
   *
   * @return the total amount of expenses.
   */
  public double getTotalExpense() {
    return expenseList.stream().mapToDouble(Expense::getAmount).sum();
  }
  
  /**
   * Determines if the Accounting object is equal to the specified object. Checks first if the
   * parameter object has the same reference as this object. If not, checks if the parameter
   * object is an instance of Accounting. If so, checks if the income and expense lists are equal.
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
    
    return incomeList.equals(accounting.getIncomeList())
        && expenseList.equals(accounting.getExpenseList());
  }
}