package com.asare.controllers;

import com.asare.application.AsareApplication;
import com.asare.enums.Navigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private Button btnLogin;
    @FXML private Button btnExit;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;
    @FXML private Label lblEmail;
    @FXML private Label lblCreateAccount;

    private static boolean invalidLogin = false;

   public void openApp(){

       btnLogin.getScene().getWindow().hide();

       System.out.println(Navigator.MAIN);

       try {
           FXMLLoader loader = new FXMLLoader(AsareApplication.class.getResource(Navigator.MAIN.path));
           Parent parent = loader.load();
           Scene appScene = new Scene(parent);
           Stage appStage = new Stage();
           appStage.setScene(appScene);

           appStage.initModality(Modality.NONE);
           appStage.initOwner(btnLogin.getScene().getWindow());
           appStage.show();

       } catch (IOException e) {
           throw new RuntimeException(e);
       }


   }

   public void ExitApp(){

   }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
