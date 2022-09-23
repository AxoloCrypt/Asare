package com.asare.controllers;

import com.asare.application.MainApplication;
import com.asare.data.Connector;
import com.asare.data.User;
import com.asare.views.ErrorPopup;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController implements Initializable
{
    @FXML private TextField txtName, txtLastName, txtCompany, txtEmail;
    @FXML private PasswordField txtPassword, txtConfirmPassword;

    @FXML private Button btnSignUp, btnBackLogin;

    private Connector connector;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setKeyPressed();
    }

    public void init(Connector connector){
        this.connector = connector;
    }

    private void signUpUser(){
        User user = new User(txtName.getText(), txtLastName.getText(), txtCompany.getText(),
                txtEmail.getText(), txtPassword.getText());

        /*
        try {
            connector.signUpUser(user);
        } catch (SQLException e) {
            new ErrorPopup(e);
        }finally {
            connector.closeConnection();
        }

         */

    }

    private void returnToLoginScene(MouseEvent event){
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void returnToLoginScene(KeyEvent event){
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        Parent root;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setKeyPressed() {

        txtName.setFocusTraversable(false);
        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                txtLastName.requestFocus();
            }
        });

        txtLastName.setFocusTraversable(false);
        txtLastName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                txtCompany.requestFocus();
        });

        txtCompany.setFocusTraversable(false);
        txtCompany.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                txtEmail.requestFocus();
        });

        txtEmail.setFocusTraversable(false);
        txtEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                txtPassword.requestFocus();
        });

        txtPassword.setFocusTraversable(false);
        txtPassword.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                txtConfirmPassword.requestFocus();
        });

        txtConfirmPassword.setFocusTraversable(false);
        txtConfirmPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                btnSignUp.requestFocus();
        });


        btnSignUp.setFocusTraversable(false);
        btnSignUp.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER){
                    signUpUser();
                    returnToLoginScene(event);
                }
        });

        btnSignUp.setOnMouseClicked(event -> {
            if(!txtConfirmPassword.getText().equals(txtPassword.getText()) || txtName.getText().trim().isEmpty())
                return;

            signUpUser();
            returnToLoginScene(event);
        });

        btnBackLogin.setFocusTraversable(false);
        btnBackLogin.setOnMouseClicked(this::returnToLoginScene);

    }

}
