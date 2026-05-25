module com.iessanalberto.dam1.ray_barber_shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.iessanalberto.dam1.ray_barber_shop to javafx.fxml;
    exports com.iessanalberto.dam1.ray_barber_shop;
}