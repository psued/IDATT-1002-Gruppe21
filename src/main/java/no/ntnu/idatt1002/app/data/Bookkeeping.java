package no.ntnu.idatt1002.app.data;

import java.util.ArrayList;

/**
 * Bookkeeping interface is used to keep track of all the income and expenses for either
 * budgeting for accounting.
 */
public interface Bookkeeping {
  
  /**
   * Method for adding an income to the income list
   *
   * @param income the income to be added
   */
  void addEquity(Income income);
  
  /**
   * Method for adding an expense to the expense list
   *
   * @param expense the expense to be added
   */
  void addExpense(Expense expense);
  
  /**
   * Method for getting the income list
   *
   * @return the income list
   */
  ArrayList<Income> getIncomeList();
  
  /**
   * Method for getting the expense list
   *
   * @return the expense list
   */
  ArrayList<Expense> getExpenseList();
  
  /**
   * Method for getting the total income
   *
   * @return the total income amount
   */
  double getTotalIncome();
  
  /**
   * Method for getting the total expense
   *
   * @return the total expense amount
   */
  double getTotalExpense();
}
