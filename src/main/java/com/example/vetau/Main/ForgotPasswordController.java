package com.example.vetau.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ForgotPasswordController {
    @FXML
    private Button close_btn_forgotPass;

    @FXML
    private TextField email_forgotPass;

    @FXML
    private Button minimize_btn_forgotPass;

    @FXML
    private Button ok_btn;

    @FXML
    private TextField username_forgotPass;
    @FXML
    private AnchorPane forgotPass_form;

    public void forgotPass_close() {
        System.exit(0);
    }

    public void forgotPass_minimize()
    {
        Stage stage = (Stage) forgotPass_form.getScene().getWindow();
        stage.setIconified(true);
    }
}
