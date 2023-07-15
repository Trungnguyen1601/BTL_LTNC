package com.example.vetau.TableView.InformationView;

import com.example.vetau.Show.Show_Window;
import com.example.vetau.helpers.Database;
import com.example.vetau.models.Account;
import com.example.vetau.models.Passenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InformationPassengerController implements Initializable {

    @FXML
    private Button dashbroard_form_btn;

    @FXML
    private Button quanlytau_form_btn;
    @FXML
    private Button train_form_btn;
    @FXML
    private TextField search_customer_txt ;
    @FXML
    private TableView<Passenger> Customer_Table;

    @FXML
    private TableColumn<Passenger, String> Email;

    @FXML
    private TableColumn<Passenger, String> ID_Khachhang;

    @FXML
    private TableColumn<Passenger, String> Name;

    @FXML
    private TableColumn<Passenger, String> Password;

    @FXML
    private TableColumn<Passenger, String> PhoneNumber;

    @FXML
    private TableColumn<Passenger, String> Username;

    @FXML
    private Button dashboard_close;
    @FXML
    private Button dashboard_minus;
    @FXML
    private TableColumn<Passenger, Button> Delete_Col;
    @FXML
    private TableColumn<Passenger, Button> Infor_Col;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Passenger customerInformation = null;
    Stage stage_dashbroard, stage_quanlytau = new Stage();

    private static String ID_Customer;

    public static String getID_Customer() {
        return ID_Customer;
    }

    ObservableList<Passenger> CustomerList = FXCollections.observableArrayList();

    @FXML
    void Setdashboard_close(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void Setdashboard_minus(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    private void loadData()
    {
        connection = Database.connectionDB();
        refreshTable();

        ID_Khachhang.setCellValueFactory(new PropertyValueFactory<>("ID_Passenger"));
        Name.setCellValueFactory(new PropertyValueFactory<>("ho_va_Ten"));
        PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        Username.setCellValueFactory(new PropertyValueFactory<>("Username"));
        Password.setCellValueFactory(new PropertyValueFactory<>("Password"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Delete_Col.setCellValueFactory(new PropertyValueFactory<>("DeleteButton"));
        Infor_Col.setCellValueFactory(new PropertyValueFactory<>("InforButton"));

        Delete_Col.setCellFactory(param -> new TableCell<>() {
            Button deleteButton = new Button("Xóa");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Passenger customerInformation = getTableView().getItems().get(getIndex());
                        CustomerList.remove(customerInformation);
                        Delete_inDB(customerInformation.getID_Passenger());
                    });
                }
            }
        });

        Infor_Col.setCellFactory(param -> new TableCell<>() {
            Button inforButton = new Button("Xem");
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(inforButton);
                    inforButton.setOnAction(event -> {
                        Passenger customerInformation = getTableView().getItems().get(getIndex());
                        ID_Customer = customerInformation.getID_Passenger();
                        String FXMLPATH = "/DashBroard/InformationView/Information_passenger.fxml";
                        try {
                            Show_Window showWindow = new Show_Window();
                            showWindow.Show(FXMLPATH);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        });
        searchCustomer();
    }

    private void refreshTable() {
        CustomerList.clear();
        Account account = new Account();
        try {
            query = "SELECT \n" +
                    "khach_hang.Ten,\n" +
                    "khach_hang.ID_Khachhang,\n" +
                    "khach_hang.Email,\n" +
                    "    khach_hang.SDT,\n" +
                    "    account_user.ID_Account,\n" +
                    "    account_user.Username,\n" +
                    "    account_user.Password\n" +
                    "\tFrom khach_hang\n" +
                    "    inner join account_user on khach_hang.ID_Account = account_user.ID_Account";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                account = new Account(resultSet.getString("ID_Account"), resultSet.getString("Username"), resultSet.getString("Password"));
                CustomerList.add(new Passenger(
                        resultSet.getString("ID_Khachhang"),
                        resultSet.getString("Ten"),
                        resultSet.getString("SDT"),
                        account,
                        resultSet.getString("Email")));
            }
            Customer_Table.setItems(CustomerList);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void Delete_inDB(String ID)
    {

        query = "DELETE FROM khach_hang WHERE ID_Khachhang = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,ID);
            int check = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchCustomer() {
        FilteredList<Passenger> filteredList = new FilteredList<>(CustomerList, b -> true);
        search_customer_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(customerInformation -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (customerInformation.getID_Passenger().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (customerInformation.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (customerInformation.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (customerInformation.getPhone().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Passenger> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(Customer_Table.comparatorProperty());
        Customer_Table.setItems(sortedList);
    }
    @FXML
    void Switch_quanlytau(MouseEvent event) throws IOException {
        stage_dashbroard =  (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Parent root2 = FXMLLoader.load(getClass().getResource("/DashBroard/Quanlytau/quanlytau.fxml"));
        Scene scene_quanlytau = new Scene(root2);
        stage_quanlytau.initStyle(StageStyle.TRANSPARENT);
        stage_quanlytau.setTitle("Quan lý tàu");
        stage_quanlytau.setScene(scene_quanlytau);

        stage_quanlytau.show();
        stage_dashbroard.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }
}

