package org.example.analyticsv5;

import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalysisView extends VBox {
    private static final List<String> CATEGORIES = Arrays.asList(
            "Shopping", "Education", "Electronics", "Entertainment", "Food and Beverages",
            "Health and Beauty", "Medical", "Transportation", "Other Expenses"
    );

    private PieChart pieChart;
    private BarChart<String, Number> barChart;
    private List<ExpenseData> expenseDataList;
    private Label totalExpenseLabel;



    public AnalysisView() {
        pieChart = new PieChart();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Category");
        yAxis.setLabel("Amount (RM)");
        totalExpenseLabel = new Label();

        // Rotate the labels for bar graph to avoid overlap
        xAxis.setTickLabelRotation(45);

        xAxis.setTickLabelGap(10);

        getChildren().addAll(totalExpenseLabel,pieChart, barChart);
        loadData();
        updateChart(1); // Default month
    }

    private void loadData() {
        expenseDataList = new ArrayList<>();
        String fileName = "Transaction.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header line
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length >= 8) {  // Adjusted to the new format with more fields
                    int userId = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    double amount = Double.parseDouble(parts[2]);
                    String type = parts[3];
                    String category = parts[4];
                    // Remaining parts can be ignored or used as needed
                    expenseDataList.add(new ExpenseData(userId, month, amount, type, category));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateChart(int month) {
        pieChart.getData().clear();
        barChart.getData().clear();

        List<PieChart.Data> pieChartData = new ArrayList<>();
        XYChart.Series<String, Number> barChartSeries = new XYChart.Series<>();
        double totalExpense = 0;

        // Calculate total expenses for the month
        for (ExpenseData data : expenseDataList) {
            if (data.getMonth() == month && data.getType().equals("expense")) {
                totalExpense += data.getAmount();
            }
        }
        totalExpenseLabel.setText("Total Expense: RM" +totalExpense);
        totalExpenseLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
        totalExpenseLabel.setPadding(new Insets(0,10,10,20));

        // Create pie chart data
        for (String category : CATEGORIES) {
            double categoryTotal = 0;
            for (ExpenseData data : expenseDataList) {
                if (data.getMonth() == month && data.getType().equals("expense") && data.getCategory().equals(category)) {
                    categoryTotal += data.getAmount();
                }
            }
            if (categoryTotal > 0) {
                pieChartData.add(new PieChart.Data(category, categoryTotal));
            }
        }

        // Add all categories to the bar chart
        for (String category : CATEGORIES) {
            double categoryAmount = 0;
            for (ExpenseData data : expenseDataList) {
                if (data.getMonth() == month && data.getType().equals("expense") && data.getCategory().equals(category)) {
                    categoryAmount += data.getAmount();
                }
            }
            barChartSeries.getData().add(new XYChart.Data<>(category, categoryAmount));
        }

        pieChart.getData().addAll(pieChartData);
        barChart.getData().add(barChartSeries);

        for (PieChart.Data slice : pieChartData) {
            double percentage = (slice.getPieValue() / totalExpense) * 100;
            slice.setName(slice.getName() + " " + String.format("%.2f%%", percentage) + " (RM"+slice.getPieValue()+")");
        }

    }
}
