package org.example.analyticsv5;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AnalysisApp extends Application{
    private BorderPane root;
    private AnalysisView analysisView;
    private ComboBox<String> monthSelector;

    private static final String[] MONTHS = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expense Analysis");

        root = new BorderPane();

        analysisView = new AnalysisView();

        monthSelector = new ComboBox<>();
        monthSelector.getItems().addAll(MONTHS);
        monthSelector.setValue(MONTHS[0]);
        monthSelector.setOnAction(e -> {
            int selectedMonthIndex = monthSelector.getSelectionModel().getSelectedIndex() + 1;
            analysisView.updateChart(selectedMonthIndex);
        });



        root.setLeft(monthSelector);
        root.setCenter(analysisView);
        //root.setTop(totalExpenseLabel);

        Scene scene = new Scene(root, 800, 600);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

}