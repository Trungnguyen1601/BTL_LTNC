package com.example.customer;

import com.example.customer.database.database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private TextField infor_CCCD_txt;

    @FXML
    private TextField infor_address_txt;

    @FXML
    private TextField infor_dob_txt;

    @FXML
    private TextField infor_email_txt;

    @FXML
    private TextField infor_gender_txt;

    @FXML
    private TextField infor_name_txt;

    @FXML
    private TextField infor_phone_txt;
    @FXML
    private TextField infor_password_txt;

    @FXML
    private TextField infor_username_txt;

    @FXML
    private Button Infor_close_btn;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    void Setdashbroaed_close(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void loadData() {
        connection = database.connectionDb();
        String Infor_ID_Khachhang = HelloController.getID_Customer();
        query = "SELECT khach_hang.ID_Khachhang," +
                "    khach_hang.Ten,\n" +
                "    khach_hang.CCCD,\n" +
                "    khach_hang.Ngay_sinh,\n" +
                "    khach_hang.Gioi_tinh,\n" +
                "    khach_hang.Email,\n" +
                "    khach_hang.Dia_chi,\n" +
                "    khach_hang.SDT,\n" +
                "    account_user.Username,\n" +
                "    account_user.Password\n" +
                "    FROM khach_hang\n" +
                "    INNER JOIN account_user ON khach_hang.ID_Account = account_user.ID_Account " +
                "    WHERE khach_hang.ID_Khachhang = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Infor_ID_Khachhang);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                infor_name_txt.setText(resultSet.getString("Ten"));
                infor_CCCD_txt.setText(resultSet.getString("CCCD"));
                infor_dob_txt.setText(resultSet.getString("Ngay_sinh"));
                infor_gender_txt.setText(resultSet.getString("Gioi_tinh"));
                infor_email_txt.setText(resultSet.getString("Email"));
                infor_address_txt.setText(resultSet.getString("Dia_chi"));
                infor_phone_txt.setText(resultSet.getString("SDT"));
                infor_username_txt.setText(resultSet.getString("Username"));
                infor_password_txt.setText(resultSet.getString("Password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }
}
