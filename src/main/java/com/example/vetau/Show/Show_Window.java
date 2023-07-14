package com.example.vetau.Show;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Show_Window {
    private static double x = 0;
    private static double y = 0;
    public Stage Show(String FXMLPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(FXMLPath));
        Scene scene1 = new Scene(root);
        root.setOnMousePressed((MouseEvent event)->{
            x = event.getSceneX();
            y = event.getSceneY();
        });
        Stage stage = new Stage();
        root.setOnMouseDragged((MouseEvent event)->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Đăng nhập");
        stage.setScene(scene1);
        stage.show();
        return stage;
    }


}
