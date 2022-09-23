package com.asare.controllers;

import com.asare.data.Aproximation;
import com.asare.exceptions.EmptyAproximationNameException;
import com.asare.views.AproximationTab;
import com.asare.views.ErrorPopup;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateAproximationController implements Initializable
{
    @FXML private TextField txtName;
    private MainController mainController;


    public String getName(){
        return txtName.getText();
    }

    /*
    @param: None
    @return: void
    Create and add a tab on the aproximations tab pane from mainController
     */
    public void createAproximationTab() throws EmptyAproximationNameException{

        mainController.getTabAproximations().getTabs().add(new AproximationTab(getName(), mainController));
        mainController.getAproximations().add(new Aproximation(getName()));
    }

    public void init(MainController mainController){
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtName.setFocusTraversable(false);
        txtName.setPromptText("Name");

        txtName.setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.ENTER){

                try{
                    if (getName().trim().isEmpty())
                        throw new EmptyAproximationNameException();

                }catch (EmptyAproximationNameException e){
                    new ErrorPopup(e);
                    return;
                }

                Stage stage = (Stage) txtName.getScene().getWindow();
                stage.close();
                createAproximationTab();
            }

        });
    }
}
