package com.example.vetau.Show;

import javafx.scene.control.Button;

public class Button_Extend {
    private Button Detail_Button;
    private Button Update_Button;
    private Button Delete_Button;

    public Button_Extend(Button detail_Button, Button update_Button, Button delete_Button) {

        Detail_Button = detail_Button;
        Update_Button = update_Button;
        Delete_Button = delete_Button;
    }

    public Button getDetail_Button() {
        return Detail_Button;
    }

    public void setDetail_Button(Button detail_Button) {
        Detail_Button = detail_Button;
    }

    public Button getUpdate_Button() {
        return Update_Button;
    }

    public void setUpdate_Button(Button update_Button) {
        Update_Button = update_Button;
    }

    public Button getDelete_Button() {
        return Delete_Button;
    }

    public void setDelete_Button(Button delete_Button) {
        Delete_Button = delete_Button;
    }
}
