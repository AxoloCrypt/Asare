package com.asare.views;

import com.asare.application.MainApplication;
import com.asare.controllers.ErrorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorPopup
{
    public ErrorPopup(Exception e){

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("errorPopUp.fxml"));

        Parent root;
        try {
            root = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.getIcons().add(new Image("https://github.com/AxoloCrypt/Asare/blob/test/src/main/resources/com/asare/images/Logo.png?raw=true"));
        stage.setScene(scene);

        ErrorController errorController = loader.getController();
        errorController.setLblText(e);

        stage.show();
    }


}
