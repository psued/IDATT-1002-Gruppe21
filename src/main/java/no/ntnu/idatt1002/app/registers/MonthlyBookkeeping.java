package no.ntnu.idatt1002.app.registers;

import java.io.Serializable;

import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;

public class MonthlyBookkeeping implements Serializable {

    private final Budgeting budgeting;
    private final Accounting accounting;
    private final int month;
    private final int year;

    public MonthlyBookkeeping(int month, int year) {
        budgeting = new Budgeting();
        accounting = new Accounting();
        this.month = month;
        this.year = year;
    }
    public Budgeting getBudgeting() {
        return budgeting;
    }

    public Accounting getAccounting() {
        return accounting;
    }

    public int getMonth() {
        return month;   
    }

    public int getYear() {
        return year;
    }

    public double getBudgetNet() {
        // Get the difference between the income and expenses
        return budgeting.getTotalIncome() - budgeting.getTotalExpense();
    }

    public double getAccountingNet() {
        // Get the difference between the income and expenses
        return accounting.getTotalIncome() - accounting.getTotalExpense();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MonthlyBookkeeping)) {
            return false;
        }
        MonthlyBookkeeping monthlyBookkeeping = (MonthlyBookkeeping) o;
        return month == monthlyBookkeeping.month && year == monthlyBookkeeping.year;
    }

    @Override
    public int hashCode()
    {
        int result = 17;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }
}
