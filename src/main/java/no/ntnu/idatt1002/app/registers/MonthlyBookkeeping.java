package no.ntnu.idatt1002.app.registers;

import java.io.Serializable;

import java.time.LocalDate;
import java.time.YearMonth;
import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;

public class MonthlyBookkeeping implements Serializable {

    private final Budgeting budgeting;
    private final Accounting accounting;
    private final YearMonth yearMonth;

    public MonthlyBookkeeping(YearMonth yearMonth) {
        budgeting = new Budgeting();
        accounting = new Accounting();
        this.yearMonth = yearMonth;
    }
    public Budgeting getBudgeting() {
        return budgeting;
    }

    public Accounting getAccounting() {
        return accounting;
    }

    public int getMonth() {
        return yearMonth.getMonthValue();
    }

    public int getYear() {
        return yearMonth.getYear();
    }
    
    public YearMonth getYearMonth() {
        return yearMonth;
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
        return yearMonth.equals(monthlyBookkeeping.getYearMonth());
    }

    @Override
    public int hashCode() {
        return yearMonth.hashCode();
    }
}
