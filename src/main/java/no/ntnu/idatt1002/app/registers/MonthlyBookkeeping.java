package no.ntnu.idatt1002.app.registers;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;
import no.ntnu.idatt1002.app.bookkeeping.Accounting;
import no.ntnu.idatt1002.app.bookkeeping.Bookkeeping;
import no.ntnu.idatt1002.app.bookkeeping.Budgeting;
import no.ntnu.idatt1002.app.transactions.Transaction;

public class MonthlyBookkeeping implements Serializable {

    private final Budgeting budgetingPersonal;
    private final Budgeting budgetingWork;
    private final Accounting accountingPersonal;
    private final Accounting accountingWork;
    private final YearMonth yearMonth;

    public MonthlyBookkeeping(YearMonth yearMonth) {
        budgetingPersonal = new Budgeting();
        budgetingWork = new Budgeting();
        accountingPersonal = new Accounting();
        accountingWork = new Accounting();
        
        this.yearMonth = yearMonth;
    }
    
    public Budgeting getBudgetingPersonal() {
        return budgetingPersonal;
    }
    
    public Budgeting getBudgetingWork() {
        return budgetingWork;
    }

    public Accounting getAccountingPersonal() {
        return accountingPersonal;
    }
    
    public Accounting getAccountingWork() {
        return accountingWork;
    }
    
    public Bookkeeping getBookkeeping(boolean isAccounting, boolean isPersonal) {
        return isAccounting
            ? (isPersonal ? accountingPersonal : accountingWork)
            : (isPersonal ? budgetingPersonal : budgetingWork);
    }
    
    public Bookkeeping getTotalBookkeeping(boolean isAccounting) {
        if (isAccounting) {
            Accounting totalAccounting = new Accounting();
            List<Transaction> transactions = Stream.concat(
                accountingPersonal.getTransactions().stream(),
                accountingWork.getTransactions().stream()
            ).toList();
            transactions.forEach(totalAccounting::addTransaction);
            return totalAccounting;
        } else {
            Budgeting totalBudgeting = new Budgeting();
            List<Transaction> transactions = Stream.concat(
                budgetingPersonal.getTransactions().stream(),
                budgetingWork.getTransactions().stream()
            ).toList();
            transactions.forEach(totalBudgeting::addTransaction);
            return totalBudgeting;
        }
    }
    
    public YearMonth getYearMonth() {
        return yearMonth;
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
