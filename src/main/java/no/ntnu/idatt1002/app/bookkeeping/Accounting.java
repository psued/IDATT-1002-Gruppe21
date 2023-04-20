package no.ntnu.idatt1002.app.bookkeeping;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Transaction;

/**
 * The Accounting class represents a bookkeeping system for tracking income and
 * expenses for the accounting of a project.
 * It implements the Bookkeeping interface and is Serializable for serialization
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
   * Adds an Income object to the income list.
   *
   * @param income the Income object to add.
   */
  public void addIncome(Income income) {
    incomeList.add(income);
  }

  /**
   * Adds a list of Income objects to the income list.
   *
   * @param incomes the list of Income objects to add.
   */
  public void addEquities(ArrayList<Income> incomes) {
    incomeList.addAll(incomes);
  }

  /**
   * Adds an Expense object to the expense list.
   *
   * @param expense the Expense object to add.
   */
  public void addExpense(Expense expense) {
    expenseList.add(expense);
  }
  
  /**
   * Adds a Transaction object to the income or expense list.
   *
   * @param transaction the Transaction object to add.
   */
  @Override
  public void addTransaction(Transaction transaction) {
    if (transaction instanceof Income income) {
      addIncome(income);
    } else if (transaction instanceof Expense expense) {
      addExpense(expense);
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
   * Adds a list of Expense objects to the expense list.
   *
   * @param expenses the list of Expense objects to add.
   */
  public void addExpenses(ArrayList<Expense> expenses) {
    expenseList.addAll(expenses);
  }

  /**
   * Returns the list of Income objects.
   *
   * @return the list of Income objects.
   */
  public ArrayList<Income> getIncomeList() {
    return new ArrayList<>(incomeList);
  }

  /**
   * Returns the list of Expense objects.
   *
   * @return the list of Expense objects.
   */
  public ArrayList<Expense> getExpenseList() {
    return new ArrayList<>(expenseList);
  }
  
  @Override
  public List<Transaction> getTransactions() {
    List<Transaction> transactions = new ArrayList<>();
    transactions.addAll(incomeList);
    transactions.addAll(expenseList);
    return transactions;
  }
  
  /**
   * Returns the total amount of income.
   *
   * @return the total amount of income.
   */
  public double getTotalIncome() {
    return incomeList.stream().mapToDouble(Income::getAmount).sum();
  }

  /**
   * Returns the total amount of expenses.
   *
   * @return the total amount of expenses.
   */
  public double getTotalExpense() {
    return expenseList.stream().mapToDouble(Expense::getAmount).sum();
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