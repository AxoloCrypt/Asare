package com.asare.controllers;

import com.asare.data.Materials;
import com.asare.data.Services;
import com.asare.exceptions.EmptyRecordNameException;
import com.asare.views.ErrorPopup;
import com.asare.views.RecordPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddRecordController implements Initializable
{
    private MainController mainController;
    private String recordType;

    @FXML private Label lblRecord;
    @FXML private Label lblCost;
    @FXML private Button btnOk;
    @FXML private TextField txtName;
    @FXML private TextField txtCost;
    @FXML private TextArea txtDescription;


    public AddRecordController() {}

    /*
    @param: None
    @return: void
    Gets the input of the textfields and textarea of the frame and creates a new recordPane with them
    Depending of the record type, the recordPane will be added on materials or services VBoxes from the
    mainController
     */
    public void registerRecord(){

        boolean isMaterial = recordType.toLowerCase(Locale.ROOT).equals("material");

        RecordPane recordPane = new RecordPane(txtName.getText(), txtCost.getText(), txtDescription.getText(), mainController, isMaterial);

        if (isMaterial){
            mainController.getvBoxMaterials().getChildren().add(recordPane);
            mainController.getMaterials().getRecords().add(new Materials(txtName.getText(), new BigDecimal(txtCost.getText()), txtDescription.getText()));
        }
        else{
            mainController.getvBoxServices().getChildren().add(recordPane);
            mainController.getServices().getRecords().add(new Services(txtName.getText(), new BigDecimal(txtCost.getText()), txtDescription.getText()));
        }

    }

    private void checkUserInput() throws NumberFormatException, EmptyRecordNameException {

        if(txtName == null || txtName.getText().trim().isEmpty())
            throw new EmptyRecordNameException();

        if(!txtCost.getText().matches(".*\\d.*"))
            throw new NumberFormatException();
    }

    /*
    Set the main controller to communicate each other and the record type
     */
    public void init(MainController mainController, String recordType) {
        this.mainController = mainController;
        this.recordType = recordType;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblRecord.setText("Name");
        lblCost.setText("Cost");

        btnOk.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                Stage stage = (Stage) btnOk.getScene().getWindow();
                stage.close();
            }
        });

        txtName.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                txtCost.requestFocus();
        });

        txtCost.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                txtDescription.requestFocus();
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){

                try{
                    checkUserInput();
                }catch (NumberFormatException | EmptyRecordNameException e){
                    new ErrorPopup(e);
                    return;
                }

                txtDescription.setDisable(true);
                Stage stage = (Stage) txtDescription.getScene().getWindow();
                registerRecord();
                stage.close();
            }
        });

    }
}
