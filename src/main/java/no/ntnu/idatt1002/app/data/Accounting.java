package no.ntnu.idatt1002.app.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for accounting
 * 
 * @author Ari Maman, Lars Mikkel Lødeng Nilsen, Trygve Jørgensen, Ingar Asheim
 */

public class Accounting implements Bookkeeping, Serializable {
  
  ArrayList<Income> incomeList;
  ArrayList<Expense> expenseList;

  /**
   * The constructor for this class
   */
  public Accounting() {
    incomeList = new ArrayList<>();
    expenseList = new ArrayList<>();
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
   * Method for adding incomes to the income list
   * 
   * @param incomes the incomes to be added
   */
  public void addEquities(ArrayList<Income> incomes) {
    incomeList.addAll(incomes);
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
   * Method for adding expenses to the expense list
   * 
   * @param expenses the expenses to be added
   */
  public void addExpenses(ArrayList<Expense> expenses) {
    expenseList.addAll(expenses);
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
    return incomeList.stream().mapToDouble(Income::getAmount).sum();
  }

  /**
   * Method for getting the total expense
   * 
   * @return the total expense
   */
  public double getTotalExpense() {
    return expenseList.stream().mapToDouble(Expense::getAmount).sum();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Budgeting)) {
      return false;
    }
    
    Budgeting budgeting = (Budgeting) obj;
    return incomeList.equals(budgeting.getIncomeList()) && expenseList.equals(budgeting.getExpenseList());
  }
}
