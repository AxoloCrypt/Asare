package com.asare.controllers;

import com.asare.exceptions.EmptyAproximationNameException;
import com.asare.exceptions.EmptyRecordNameException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class ErrorController
{
    @FXML private Label lblError;

    public void setLblText(Exception e){

        if (e instanceof SQLException){
            lblError.setText("Error when connecting to the database");
        }
        else if (e instanceof EmptyRecordNameException){
            lblError.setText("The name of the record must not be empty");
        } else if (e instanceof EmptyAproximationNameException){
            lblError.setText("The approximation must have a name");
        } else if (e instanceof  NumberFormatException) {
            lblError.setText("Only numbers are accepted in cost field");
        }

    }


}
