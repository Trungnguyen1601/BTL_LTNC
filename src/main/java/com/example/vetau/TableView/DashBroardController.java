package com.example.vetau.TableView;

import com.example.vetau.Show.Show_Window;
import com.example.vetau.helpers.Database;
import com.example.vetau.models.Train;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashBroardController implements Initializable {

    @FXML
    private Button bcthongke_form;

    @FXML
    private Button clear_btn_id;

    @FXML
    private Button dashbroaed_close;

    @FXML
    private Button dashbroard_form;

    @FXML
    private Button dashbroard_minus;

    @FXML
    private Button delete_btn_id;

    @FXML
    private TableColumn<Train, String> gaden_col;

    @FXML
    private TextField gaden_id;

    @FXML
    private TableColumn<Train, String> gadi_col;

    @FXML
    private TextField gadi_id;

    @FXML
    private TableColumn<Train, String> giodi_col;

    @FXML
    private TextField giodi_id;

    @FXML
    private Button insert_btn_id;

    @FXML
    private Button passenger_fom;

    @FXML
    private Button search_btn_id;

    @FXML
    private Button signout_btn;

    @FXML
    private TableColumn<Train, String> tau_col;

    @FXML
    private TextField tau_id;

    @FXML
    private Button train_mana_form;

    @FXML
    private Button dashbroard_phongto;
    @FXML
    private Button update_btn_id;
    @FXML
    private TableView<Train> Train_table;
    @FXML
    private TableColumn<Train, Button> Update_col;

    @FXML
    private TableColumn<Train, Button> Delete_col;
    @FXML
    private DatePicker Ngayden_id;

    @FXML
    private DatePicker Ngaydi_id;
    @FXML
    private TableColumn<Train, LocalDate> ngayden_col;

    @FXML
    private TableColumn<Train, LocalDate> ngaydi_col;
    @FXML
    private Label chucnang_id;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Train train = null;

    private static int Train_id;

    public static int getTrain_id() {
        return Train_id;
    }

    ObservableList<Train> TrainList = FXCollections.observableArrayList();

    @FXML
    void enlargeWindow(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

    }
    private void loadDate()
    {
        connection = Database.connectionDB();
        refreshTable();

        gadi_col.setCellValueFactory(new PropertyValueFactory<>("Gadi"));
        gaden_col.setCellValueFactory(new PropertyValueFactory<>("Gaden"));
        tau_col.setCellValueFactory(new PropertyValueFactory<>("Tau"));
        giodi_col.setCellValueFactory(new PropertyValueFactory<>("Giodi"));
        Delete_col.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        Update_col.setCellValueFactory(new PropertyValueFactory<>("inforButton"));
        ngaydi_col.setCellValueFactory(new PropertyValueFactory<>("ngaydi"));
        ngayden_col.setCellValueFactory(new PropertyValueFactory<>("ngayden"));

        Delete_col.setCellFactory(param -> new TableCell<>() {
            Button deleteButton = new Button("Xóa");



            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Train train = getTableView().getItems().get(getIndex());
                        TrainList.remove(train);
                        Delete_inDB(train.getID_train());
                        System.out.println("Xóa sinh viên: " + train.getID_train());
                        // Gọi phương thức xóa sinh viên từ MySQL tại đây
                    });
                }
            }
        });

        Update_col.setCellFactory(param -> new TableCell<>() {
            final Button inforButton = new Button("Chi tiết");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(inforButton);

                    inforButton.setOnAction(event -> {
                        Train train = getTableView().getItems().get(getIndex());
                        Train_id = train.getID_train();
                        String FXMLPATH = "/DashBroard/InformationView/Information_train.fxml";
                        try {
                            Show_Window showWindow =new Show_Window();
                            showWindow.Show(FXMLPATH);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Xem sinh viên: " + train.getID_train());
                        // Gọi phương thức sửa thông tin sinh viên từ MySQL tại đây
                    });
                }
            }
        });
    }

    private void refreshTable() {
        TrainList.clear();

        try {
            query = "SELECT * FROM chuyen_tau";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                TrainList.add(new Train(
                        resultSet.getInt("ID_chuyentau"),
                        resultSet.getString("Ga_di"),
                        resultSet.getString("Ga_den"),
                        resultSet.getInt("ID_tau"),
                        resultSet.getString("Gio_di"),
                        resultSet.getDate("Ngay_di"),
                        resultSet.getDate("Ngay_den")));
                Train_table.setItems(TrainList);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void Delete_inDB(int ID)
    {

        query = "DELETE FROM chuyen_tau WHERE ID_chuyentau = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,ID);
            int check = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
    public int Count_Database_chuyentau()
    {
        int totalCount = 0;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM chuyen_tau");
            if(resultSet.next())
            {
                totalCount = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return totalCount;
    }
    public int MaxID_Database_chuyentau()
    {
        int MaxID = 0;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT MAX(ID_chuyentau) AS MaxId FROM chuyen_tau");
            if(resultSet.next())
            {
                MaxID = resultSet.getInt("MaxId");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return MaxID;
    }
    public void Add_train()
    {

        query = "INSERT INTO chuyen_tau (ID_chuyentau,Ga_di, Ga_den, ID_tau, Gio_di,Ngay_di,Ngay_den) VAlUES (?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,MaxID_Database_chuyentau()+1);
            preparedStatement.setString(2,gadi_id.getText());
            preparedStatement.setString(3,gaden_id.getText());
            preparedStatement.setInt(4, Integer.parseInt(tau_id.getText()));
            preparedStatement.setString(5,giodi_id.getText());
            preparedStatement.setDate(6,java.sql.Date.valueOf(Ngaydi_id.getValue()));
            preparedStatement.setDate(7,java.sql.Date.valueOf(Ngayden_id.getValue()));

            int check = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        refreshTable();
    }
    public void Search_train()
    {
        int ID_tau = 0;
        Date ndi = null;
        Date nden = null;
        query = "SELECT * FROM chuyen_tau \n" +
                "WHERE (Ga_di = ? OR ? is null ) " +
                "AND (Ga_den = ? OR ? is null ) " +
                "AND (ID_tau = ? OR ? = 0) " +
                "AND (Gio_di = ? OR ? is null ) " +
                "AND (Ngay_di = ? OR ? is null ) " +
                "AND (Ngay_den = ? OR ? is null ) ";
        connection = Database.connectionDB();
        if (tau_id.getText() != null && !tau_id.getText().isEmpty())
        {
            ID_tau = Integer.parseInt(tau_id.getText());
        }
        if (Ngaydi_id.getValue() != null)
        {
            ndi = java.sql.Date.valueOf(Ngaydi_id.getValue());
        }
        if(Ngayden_id.getValue() != null)
        {
            nden = java.sql.Date.valueOf(Ngayden_id.getValue());
        }
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,gadi_id.getText());
            preparedStatement.setString(2,gadi_id.getText());
            preparedStatement.setString(3,gaden_id.getText());
            preparedStatement.setString(4,gaden_id.getText());
            preparedStatement.setInt(5,ID_tau);
            preparedStatement.setInt(6,ID_tau);
            preparedStatement.setString(7,giodi_id.getText());
            preparedStatement.setString(8,giodi_id.getText());
            preparedStatement.setDate(9, ndi);
            preparedStatement.setDate(10,ndi);
            preparedStatement.setDate(11, nden);
            preparedStatement.setDate(12,nden);
            resultSet = preparedStatement.executeQuery();
            TrainList.clear();
            while (resultSet.next())
            {

                TrainList.add(new Train(
                        resultSet.getInt("ID_chuyentau"),
                        resultSet.getString("Ga_di"),
                        resultSet.getString("Ga_den"),
                        resultSet.getInt("ID_tau"),
                        resultSet.getString("Gio_di"),
                        resultSet.getDate("Ngay_di"),
                        resultSet.getDate("Ngay_den")));
                Train_table.setItems(TrainList);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void Refresh_click(MouseEvent event) {
        refreshTable();

    }
    @FXML
    void Search_click(MouseEvent event) {
        chucnang_id.setText("Tìm kiếm thông tin chuyến tàu");
        Search_train();

    }
    @FXML
    void Setdashbroaed_close(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void Setdashbroard_minus(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    void Clear_Click(MouseEvent event) {
        gadi_id.setText(null);
        gaden_id.setText(null);
        giodi_id.setText(null);
        Ngaydi_id.setValue(null);
        Ngayden_id.setValue(null);
        tau_id.setText(null);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chucnang_id.setText("Trung đẹp trai");
        loadDate();
    }
}
