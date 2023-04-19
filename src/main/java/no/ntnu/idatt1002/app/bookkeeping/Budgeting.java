package no.ntnu.idatt1002.app.bookkeeping;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import no.ntnu.idatt1002.app.transactions.Expense;
import no.ntnu.idatt1002.app.transactions.Income;
import no.ntnu.idatt1002.app.transactions.Transaction;

/**
 * The Budgeting class represents a bookkeeping system for tracking income and
 * expenses for the budgeting of a project.
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
   * Method for adding an income to the income list.
   *
   * @param income the income to be added
   */
  public void addIncome(Income income) {
    incomeList.add(income);
  }
  
  /**
   * Method for adding incomes to the income list.
   *
   * @param incomes the incomes to be added
   */
  public void addEquities(ArrayList<Income> incomes) {
    incomeList.addAll(incomes);
  }

  /**
   * Method for adding an expense to the expense list.
   *
   * @param expense the expense to be added
   */
  public void addExpense(Expense expense) {
    expenseList.add(expense);
  }
  
  /**
   * Method for adding a transaction to the income or expense list.
   *
   * @param transaction the transaction to be added
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
   * Removes a transaction from the income or expense list.
   *
   * @param transaction the transaction to be removed
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
   * Method for adding expenses to the expense list.
   *
   * @param expenses the expenses to be added
   */
  public void addExpenses(ArrayList<Expense> expenses) {
    expenseList.addAll(expenses);
  }

  /**
   * Method for getting the income list.
   *
   * @return the income list
   */
  public ArrayList<Income> getIncomeList() {
    return new ArrayList<>(incomeList);
  }

  /**
   * Method for getting the expense list.
   *
   * @return the expense list
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
   * Method for getting the total income.
   *
   * @return the total income
   */
  public double getTotalIncome() {
    return incomeList.stream().mapToDouble(Income::getAmount).sum();
  }

  /**
   * Method for getting the total expense.
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
