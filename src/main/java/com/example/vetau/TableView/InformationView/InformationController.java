package com.example.vetau.TableView.InformationView;

import com.example.vetau.TableView.DashBroardController;
import com.example.vetau.helpers.Database;
import com.example.vetau.models.Train;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class InformationController implements Initializable {

    @FXML
    private Button Edit_in_infor_btn;

    @FXML
    private Button close_infor_btn;

    @FXML
    private TextField gadden_infor_text;

    @FXML
    private TextField gadi_infor_text;

    @FXML
    private TextField giodi_infor_text;

    @FXML
    private TextField idTau_infor_text;

    @FXML
    private TextField sotoa_infor_text;

    @FXML
    private TextField tentau_infor_text;

    @FXML
    private TextField ve_conlai_infor_text;

    @FXML
    private TextField veban_infor_text;
    // Edit form
    @FXML
    private Button Edit_close_btn;
    @FXML
    private AnchorPane Infor_form;

    @FXML
    private AnchorPane Edit_form;

    @FXML
    private Button OK_edit_btn;

    @FXML
    private Button edit_minus_btn;

    @FXML
    private TextField gaden_edit_text;

    @FXML
    private TextField gadi_edit_text;

    @FXML
    private TextField giodi_edit_text;

    @FXML
    private TextField soluongtoa_edit_text;

    @FXML
    private ComboBox<Integer> tau_edit_combo;
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;
    Train train = null;
    int select_train;
    @FXML
    public void setClose_infor_btn(MouseEvent event)
    {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
    @FXML
    void setedit_minus_btn(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void setEdit_close_btn(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    void setEdit_in_infor_btn(MouseEvent event) {
        Edit_form.setVisible(true);
        Infor_form.setVisible(false);
        Edit_Train();
    }

    @FXML
    void setOK(MouseEvent event1) {
        try {

            query = "SELECT idTau FROM tau";
            preparedStatement = connection.prepareStatement(query);

            // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào combobox
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("idTau"));
                int trainID = resultSet.getInt("idTau");
                tau_edit_combo.getItems().add(trainID);
            }

            // Xử lý sự kiện khi combobox được chọn
            tau_edit_combo.setOnAction(event -> {
                int selectedTrain = tau_edit_combo.getValue();
                select_train = selectedTrain;

                try {
                    String query1 = "SELECT Soluongtoa FROM tau WHERE idTau = ?";
                    PreparedStatement selectedTrainStatement = connection.prepareStatement(query1);
                    selectedTrainStatement.setInt(1, selectedTrain);
                    ResultSet resultSet1 = selectedTrainStatement.executeQuery();
                    if (resultSet1.next()) {
                        int numOfCoaches = resultSet1.getInt("Soluongtoa");
                        soluongtoa_edit_text.setText(String.valueOf(numOfCoaches));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int Train_id_edit = DashBroardController.getTrain_id();
        if(!gadi_edit_text.getText().isEmpty())
        {
            train.setGadi(gadi_edit_text.getText());
        }
        if(!gaden_edit_text.getText().isEmpty())
        {
            train.setGaden(gaden_edit_text.getText());
        }
        if (!giodi_edit_text.getText().isEmpty())
        {
            train.setGiodi(giodi_edit_text.getText());
        }
        if(select_train > 0)
        {
            train.setID_train(select_train);
        }

        query = "UPDATE chuyen_tau " +
                "SET Ga_di = ?, " +
                "Ga_den = ?, " +
                "ID_tau = ?, "+
                "Gio_di = ? \n"+

                "WHERE ID_chuyentau = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,train.getGadi());
            preparedStatement.setString(2,train.getGaden());
            preparedStatement.setInt(3,train.getID_train());
            preparedStatement.setString(4,train.getGiodi());
            preparedStatement.setInt(5,Train_id_edit);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Start()
    {
        connection = Database.connectionDB();
        int Train_ID_infor = DashBroardController.getTrain_id();
        System.out.println("Thong tin xem la " + Train_ID_infor);
        query = "SELECT" +
                "    chuyen_tau.Ga_di," +
                "    chuyen_tau.Ga_den," +
                "    chuyen_tau.Gio_di," +
                "    tau.Soluongtoa," +
                "    tau.TenTau" +
                "   FROM chuyen_tau " +
                "   INNER JOIN tau ON chuyen_tau.ID_tau = tau.idTau "+
                "   WHERE chuyen_tau.ID_chuyentau = ? ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,Train_ID_infor);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                gadi_infor_text.setText(resultSet.getString("Ga_di"));
                gadden_infor_text.setText(resultSet.getString("Ga_den"));
                giodi_infor_text.setText(resultSet.getString("Gio_di"));
                sotoa_infor_text.setText(String.valueOf(resultSet.getInt("Soluongtoa")));
                tentau_infor_text.setText(resultSet.getString("TenTau"));


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void Edit_Train()
    {
        int Train_id_edit = DashBroardController.getTrain_id();
        connection = Database.connectionDB();
        query = "SELECT * FROM chuyen_tau WHERE ID_chuyentau = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,Train_id_edit);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                train = new Train(Train_id_edit,
                        resultSet.getString("Ga_di"),
                        resultSet.getString("Ga_den"),
                        resultSet.getInt("ID_tau"),
                        resultSet.getString("Gio_di"));
            }

            try {

                query = "SELECT idTau FROM tau";
                preparedStatement = connection.prepareStatement(query);

                // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào combobox
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
//                    System.out.println(resultSet.getInt("idTau"));
                    Integer trainID = resultSet.getInt("idTau");
                    tau_edit_combo.getItems().add(trainID);
                }

                // Xử lý sự kiện khi combobox được chọn
                tau_edit_combo.setOnAction(event -> {
                    Integer selectedTrain = tau_edit_combo.getValue();
                    try {
                        String query1 = "SELECT Soluongtoa FROM tau WHERE idTau = ?";
                        PreparedStatement selectedTrainStatement = connection.prepareStatement(query1);
                        selectedTrainStatement.setInt(1, selectedTrain);
                        ResultSet resultSet1 = selectedTrainStatement.executeQuery();
                        if (resultSet1.next()) {
                            int numOfCoaches = resultSet1.getInt("Soluongtoa");
                            soluongtoa_edit_text.setText(String.valueOf(numOfCoaches));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Infor_form.setVisible(true);
        Edit_form.setVisible(false);

        Start();

    }
}
