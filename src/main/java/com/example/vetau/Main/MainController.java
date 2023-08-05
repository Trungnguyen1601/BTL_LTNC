package com.example.vetau.Main;

import com.example.vetau.helpers.Database;
import com.example.vetau.helpers.RandomString;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainController {

    @FXML
    private Button signin_btn_login;

    @FXML
    private Button signin_close;

    @FXML
    private Hyperlink signin_creataccount;

    @FXML
    private AnchorPane signin_form;

    @FXML
    private Button signin_minimize;

    @FXML
    private PasswordField signin_password;

    @FXML
    private TextField signin_username;

    @FXML
    private TextField signup_address;

    @FXML
    private Button signup_btn_signup;

    @FXML
    private TextField signup_cccd;

    @FXML
    private Button signup_close;

    @FXML
    private TextField signup_gioitinh;


    @FXML
    private AnchorPane signup_form;

    @FXML
    private Hyperlink signup_hyber_alreadyaccount;

    @FXML
    private TextField signup_usename;

    @FXML
    private Button signup_minimize;

    @FXML
    private TextField signup_name;
    @FXML
    private DatePicker signup_date;
    @FXML
    private TextField signup_email;
    @FXML
    private Hyperlink signin_forgetpassword;
    Stage stage_main, stage_forgotPass = new Stage();

    // TOOLs FOR DATABASE
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    public boolean ValidEmail(String Email)
    {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher match = pattern.matcher(Email);
        Alert alert;
        if(match.find() && match.group().equals(Email))
        {
            return true;
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();
            return false;
        }
    }

    public void signup() throws SQLException {

        String sql = "INSERT INTO khach_hang (ID_Khachhang, Ten, CCCD,Dia_chi,Ngay_sinh,Gioi_tinh,Email)" +" VALUES (?,?,?,?,?,?,?)";

        String sql_insert_account = "INSERT INTO account (ID_Account, Username, Password)" + " VALUES (?,?,?)";
        connection = Database.connectionDB();
        statement = connection.createStatement();


        int totalCount = 0;
        boolean Flag_Insert = false;
        Alert alert;
        try {
            String sql_check_have = "SElECT * FROM khach_hang WHERE CCCD = ?";
            PreparedStatement check_have = connection.prepareStatement(sql_check_have);
            check_have.setString(1,signup_cccd.getText());
            ResultSet result_check_have = check_have.executeQuery();
            if (result_check_have.next())
            {
                Flag_Insert = true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        try{
            resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM khach_hang");
            if(resultSet.next())
            {
                totalCount = resultSet.getInt("total");
            }
            String pass = RandomString.randomAlphaNumeric(8);
            // String sql = "INSERT INTO khach_hang (ID_Khachhang, Ten, CCCD,Dia_chi,Ngay_sinh,Gioi_tinh,Email)" +" VALUES (?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,"KH" + (totalCount + 1));
            preparedStatement.setString(2,signup_name.getText());
            preparedStatement.setString(3,signup_cccd.getText());
            preparedStatement.setString(4,signup_address.getText());
            preparedStatement.setDate(5,java.sql.Date.valueOf(signup_date.getValue()));
            preparedStatement.setString(6,signup_gioitinh.getText());
            preparedStatement.setString(7,signup_email.getText());
            //String sql_insert_account = "INSERT INTO account (ID_Account, Username, Password)" + " VALUES (?,?,?)";
            PreparedStatement preparedStatement_insertaccount = connection.prepareStatement(sql_insert_account);
            preparedStatement_insertaccount.setString(1,signin_username.getText());
            preparedStatement_insertaccount.setString(2,pass);
            if (Flag_Insert) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("User exist in database");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the other username");
                alert.showAndWait();

            }else if (!ValidEmail(signup_email.getText()))
            {

            }
            else if(signup_name.getText().isEmpty()||signup_address.getText().isEmpty()||signup_date.getValue().toString().isEmpty()
                    ||signup_cccd.getText().isEmpty()||signup_gioitinh.getText().isEmpty()||signup_usename.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all blank field");
                alert.showAndWait();
            }else{
                int result = preparedStatement.executeUpdate();
                if (result>0)
                {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully create a new account");
                    alert.showAndWait();

                    signup_name.setText("");
                    signup_gioitinh.setText("");
                    //signup_date.setValue();
                    signup_cccd.setText("");
                    signup_address.setText("");
                    signup_email.setText("");
                    signup_usename.setText("");

                }
                else{
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Note Message");
                    alert.setHeaderText(null);
                    alert.setContentText("UnSuccessfully create a new acconut, check data enter");
                    alert.showAndWait();
                }
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void signin()
    {
        String sql = "SELECT * FROM account_user WHERE Username = ? and Password = ?";
        connection = Database.connectionDB();
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, signin_username.getText());
            preparedStatement.setString(2,signin_password.getText());

            resultSet = preparedStatement.executeQuery();
            Alert alert;
            if (signin_username.getText().isEmpty() || signin_password.getText().isEmpty())
            {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Please fill all blank field");
                alert.showAndWait();
            }else{
                if (resultSet.next())
                {
                     alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setTitle("Information Message");
                     alert.setHeaderText(null);
                     alert.setContentText("Successfully login");
                     alert.showAndWait();
                    signin_btn_login.getScene().getWindow().hide();

                    Parent root1 = FXMLLoader.load(getClass().getResource("/DashBroard/dashbroard.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root1);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Untrue Username/Password");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchForm(ActionEvent event)
    {
        if(event.getSource() == signin_creataccount)
        {
            signin_form.setVisible(false);
            signup_form.setVisible(true);
        }else if (event.getSource() == signup_hyber_alreadyaccount)
        {
            signin_form.setVisible(true);
            signup_form.setVisible(false);
        }
    }

    public void signin_close() {
        System.exit(0);
    }

    public void signin_minimize()
    {
        Stage stage = (Stage) signin_form.getScene().getWindow();
        stage.setIconified(true);
    }
    public void signup_close()
    {
        System.exit(1);
    }
    public void signup_minimize()
    {
        Stage stage = (Stage) signup_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void signin_forgetpassword(MouseEvent event) throws IOException {
        stage_main =  (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("/Main/forgot_Password.fxml"));
        Scene scene_forgotPassword = new Scene(root);
        stage_forgotPass.initStyle(StageStyle.TRANSPARENT);
        stage_forgotPass.setScene(scene_forgotPassword);

        stage_forgotPass.show();
        stage_main.close();
    }
    public void initialize(URL url, ResourceBundle rb)
    {

    }
}
