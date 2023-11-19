module com.example.parkingfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.example.parkingfx to javafx.fxml;
    exports com.example.parkingfx;
}