package no.ntnu.idatt1002.app.data;

import java.util.ArrayList;

/**
 * Class for accounting
 * 
 * @author Ari Maman, Lars Mikkel Lødeng Nilsen, Trygve Jørgensen, Ingar Asheim
 */

public class Budgeting {

  /**
   * Class for accounting
   * 
   * @author Ari Maman, Lars Mikkel Lødeng Nilsen, Trygve Jørgensen, Ingar Asheim
   */
  ArrayList<Income> incomeList = new ArrayList<Income>();
  ArrayList<Expense> expenseList = new ArrayList<Expense>();

  /**
   * The constructor for this class
   */

  public Budgeting() {
  }

  /**
   * Method for adding an income to the income list
   *
   * @param income the income to be added
   */

  public void addIncome(Income income) {
    incomeList.add(income);
  }

  /**
   * Method for adding an expense to the expense list
   *
   * @param expense the expense to be added
   */

  public void addExpense(Expense expense) {
    expenseList.add(expense);
  }

  /**
   * Method for getting the income list
   *
   * @return the income list
   */

  public ArrayList<Income> getIncomeList() {
    return incomeList;
  }

  /**
   * Method for getting the expense list
   *
   * @return the expense list
   */

  public ArrayList<Expense> getExpenseList() {
    return expenseList;
  }

  /**
   * Method for getting the total income
   *
   * @return the total income
   */

  public double getTotalIncome() {
    double totalIncome = 0;
    for (Income income : incomeList) {
      totalIncome += income.getAmount();
    }
    return totalIncome;
  }

  /**
   * Method for getting the total expense
   *
   * @return the total expense
   */

  public double getTotalExpense() {
    double totalExpense = 0;
    for (Expense expense : expenseList) {
      totalExpense += expense.getAmount();
    }
    return totalExpense;
  }
}
