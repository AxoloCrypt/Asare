package com.asare.controllers;


import com.asare.application.MainApplication;
import com.asare.data.*;
import com.asare.views.AproximationPane;
import com.asare.views.ErrorPopup;
import com.asare.views.RecordPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final Materials materials = new Materials();
    private final Services services = new Services();
    private final History history = new History();
    private Connector connector;
    private final List<Aproximation> aproximations = new ArrayList<>();
    private User user;

    @FXML
    AddRecordController addRecordController;
    @FXML CreateAproximationController createAproximationController;

    @FXML private VBox vBoxMaterials;
    @FXML private VBox vBoxServices;
    @FXML private VBox vBoxHistory;
    @FXML private Button btnAddMaterial;
    @FXML private Button btnAddService;
    @FXML private TabPane tabAproximations;

    /*
    @param: String recordType
    @return: void
    Loads the addRecordPopUp and inits the addRecordController
     */
    public void popUpAddRecord(String recordType) throws IOException {

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("addRecordPopUp.fxml"));

        Parent root = loader.load();
        Scene addRecordScene = new Scene(root);
        Stage addStage = new Stage();
        addStage.setScene(addRecordScene);
        addStage.initModality(Modality.NONE);
        addStage.show();

        addRecordController = loader.getController();
        addRecordController.init(this, recordType);

    }

    /*
    @param: None
    @return: void
    Loads the createAproximationPopUp and inits the createAproximationController
     */
    public void createNewAproximation() throws IOException {

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("createAproximationPopUp.fxml"));

        Parent root = loader.load();
        Scene createAproximationScene = new Scene(root);
        Stage createAproximationStage = new Stage();
        createAproximationStage.setScene(createAproximationScene);
        createAproximationStage.initModality(Modality.NONE);
        createAproximationStage.show();

        createAproximationController = loader.getController();
        createAproximationController.init(this);
    }

    public void initConnection(Connector connector, User user){
        this.connector = connector;
        this.user = user;

        try {
            initializeMaterials();
            initializeServices();
            initializeAproximations();
        } catch (SQLException throwables) {
            new ErrorPopup(throwables);
            throwables.printStackTrace();
        }finally {
            this.connector.closeConnection();
        }
    }

    private void initializeMaterials() throws SQLException {
        materials.setRecords(connector.getUserMaterials(user.getEmail()));

        for (Materials material : materials.getRecords()){
            vBoxMaterials.getChildren().add(new RecordPane(material.getName(), material.getUnitCost().toString(), material.getDescription(), this, true));
            vBoxMaterials.setSpacing(2);
        }

    }

    private void initializeServices() throws SQLException{
        services.setRecords(connector.getUserServices(user.getEmail()));

        for (Services service : services.getRecords()){
            vBoxServices.getChildren().add(new RecordPane(service.getName(), service.getUnitCost().toString(), service.getDescription(), this, false));
            vBoxServices.setSpacing(2);
        }

    }

    private void initializeAproximations() throws SQLException{

        history.setSavedAproximations(connector.getUserAproximations(user.getEmail()));

        for (Aproximation aproximation : history.getSavedAproximations()){

            vBoxHistory.getChildren().add(new AproximationPane(aproximation.getIdAprox(),aproximation.getName(), aproximation.getDateCreation(), this));

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBoxServices.setStyle("-fx-font-family: 'Franklin Gothic Book'"+";-fx-font-size:9pt"+";-fx-text-fill: black; -fx-background-color: rgb(248,248,255)");
        vBoxMaterials.setStyle("-fx-font-family: 'Franklin Gothic Book'"+";-fx-font-size:9pt"+";-fx-text-fill: black; -fx-background-color: rgb(248,248,255)");
        vBoxHistory.setStyle("-fx-font-family: 'Franklin Gothic Book'"+";-fx-font-size:9pt"+";-fx-text-fill: black; -fx-background-color: rgb(248,248,255)");
        vBoxHistory.setSpacing(2);
        btnAddMaterial.setOnAction(event -> {
            try {
                popUpAddRecord("material");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnAddService.setOnAction(event -> {
            try {
                popUpAddRecord("service");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public VBox getvBoxMaterials() {
        return vBoxMaterials;
    }

    public VBox getvBoxServices() {
        return vBoxServices;
    }

    public TabPane getTabAproximations() {
        return tabAproximations;
    }


    public Materials getMaterials() {
        return materials;
    }

    public Services getServices() {
        return services;
    }

    public List<Aproximation> getAproximations() {
        return aproximations;
    }

    public History getHistory() {
        return history;
    }

    public VBox getvBoxHistory() {
        return vBoxHistory;
    }

    public void setvBoxHistory(VBox vBoxHistory) {
        this.vBoxHistory = vBoxHistory;
    }

    public Connector getConnector() {
        return connector;
    }

    public User getUser() {return user;}
}
