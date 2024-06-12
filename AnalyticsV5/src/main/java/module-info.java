module org.example.analyticsv5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.analyticsv5 to javafx.fxml;
    exports org.example.analyticsv5;
}