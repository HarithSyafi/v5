package org.example.analyticsv5;

public class ExpenseData {
    private int userId;
    private int month;
    private double amount;
    private String type;
    private String category;

    public ExpenseData(int userId, int month, double amount, String type, String category) {
        this.userId = userId;
        this.month = month;
        this.amount = amount;
        this.type = type;
        this.category = category;
    }

    public int getUserId() {
        return userId;
    }

    public int getMonth() {
        return month;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }
}
