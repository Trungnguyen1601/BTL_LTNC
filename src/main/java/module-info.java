module com.example.vetau {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.example.vetau.TableView to javafx.fxml;
    opens com.example.vetau.TableView.InformationView to javafx.fxml;
    exports com.example.vetau.TableView.InformationView;
    opens com.example.vetau.TableView.QuanLyTau to javafx.fxml;
    exports com.example.vetau.TableView.QuanLyTau;
    exports com.example.vetau.helpers;
    exports com.example.vetau.TableView;
    opens com.example.vetau.helpers to javafx.fxml;

    opens com.example.vetau.models to javafx.base;
    exports com.example.vetau.Main;
    opens com.example.vetau.Main to javafx.fxml;
}