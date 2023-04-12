package no.ntnu.idatt1002.app.data;

public class MonthlyBookkeping {

    private final Budgeting budgeting;
    private final Accounting accounting;
    private final Month month;

    // Constructor
    public MonthlyBookkeping(Month month) {
        budgeting = new Budgeting();
        accounting = new Accounting();
        this.month = month;
    }

    // Getters for private fields
    public Budgeting getBudgeting() {
        return budgeting;
    }

    public Accounting getAccounting() {
        return accounting;
    }

    public Month getMonth() {
        return month;
    }

    public double getBudgetNet() {
        // Get the difference between the income and expenses
        return budgeting.getTotalIncome() - budgeting.getTotalExpense();
    }

    public double getAccountingNet() {
        // Get the difference between the income and expenses
        return accounting.getTotalIncome() - accounting.getTotalExpense();
    }
}
