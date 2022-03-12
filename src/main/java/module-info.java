module com.example.gs {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gs to javafx.fxml;
    exports com.example.gs;
}