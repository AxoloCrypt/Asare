package com.asare.views;

import com.asare.controllers.MainController;
import com.asare.data.Materials;
import com.asare.data.Services;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Objects;

public class RecordPane extends DialogPane {

    private Label lblCost;
    private Label lblDescription;
    private Label lblName;

    private MenuButton menuActions;
    private MenuItem itemDelete;


    // Attributes for panes in aproximations
    private Button btnRemove;
    private Button btnAdd;
    private TextField txtQuantity;
    private String description;
    private BigDecimal originalCost;
    private BigDecimal currentCost;

    // For record section
    public RecordPane(String recordName, String cost, String description, MainController mainController, boolean isMaterial){

        this.setStyle("-fx-border-color: rgb(15,19,12);" + "-fx-background-color: rgb(248, 248, 255)");

        BorderPane borderPane = new BorderPane();

        lblName = new Label(recordName);
        menuActions = new MenuButton();
        menuActions.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/customMenuButton.css")).toExternalForm());

        ImageView actionsView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/points.png")).toExternalForm()));
        actionsView.setFitHeight(10);
        actionsView.setFitWidth(10);

        itemDelete = new MenuItem("Delete");
        itemDelete.setOnAction(event -> {
            menuActions.hide();

            if(isMaterial){
                mainController.getvBoxMaterials().getChildren().remove(this);

                Materials tmpMaterial = new Materials(recordName, new BigDecimal(cost), description);

                mainController.getMaterials().getRecords().remove(tmpMaterial);

                try {
                    mainController.getConnector().disableRecord(tmpMaterial);
                } catch (SQLException e) {
                    new ErrorPopup(e);
                }
                finally {
                    mainController.getConnector().closeConnection();
                }

            }
            else{
                mainController.getvBoxServices().getChildren().remove(this);

                Services tmpService = new Services(recordName, new BigDecimal(cost), description);

                mainController.getServices().getRecords().remove(tmpService);

                try{
                    mainController.getConnector().disableRecord(tmpService);
                }catch (SQLException e){
                    new ErrorPopup(e);
                }
                finally {
                    mainController.getConnector().closeConnection();
                }

            }
        });

        menuActions.setGraphic(actionsView);
        menuActions.setContentDisplay(ContentDisplay.RIGHT);
        menuActions.getItems().add(itemDelete);

        borderPane.setLeft(lblName);
        borderPane.setRight(menuActions);

        lblCost = new Label("$" + cost);
        lblCost.setAlignment(Pos.CENTER_RIGHT);

        lblDescription = new Label(description);
        lblDescription.setAlignment(Pos.CENTER_LEFT);
        lblDescription.setTextOverrun(OverrunStyle.ELLIPSIS);
        lblDescription.setEllipsisString("...");

        HBox hBox = new HBox();
        hBox.getChildren().add(lblDescription);
        hBox.getChildren().add(lblCost);

        this.setHeader(borderPane);
        this.setContent(hBox);

        this.setOnMouseClicked(event -> {

            Tab currentAproximationTab = mainController.getTabAproximations().getSelectionModel().getSelectedItem();
            int tabIndex = mainController.getTabAproximations().getSelectionModel().getSelectedIndex();

            Node node = currentAproximationTab.getContent();

            VBox vBoxRecords = (VBox) node.lookup("VBox"); //Search for vBoxRecords on the current tab

            vBoxRecords.getChildren().add(new RecordPane(lblName.getText(), new BigDecimal(lblCost.getText().replace("$", "")), lblDescription.getText(), mainController, isMaterial));

            if (isMaterial)
                mainController.getAproximations().get(tabIndex).getRecords().add(new Materials(lblName.getText(),
                        new BigDecimal(lblCost.getText().replace("$", "")), lblDescription.getText(), 1));
            else
                mainController.getAproximations().get(tabIndex).getRecords().add(new Services(lblName.getText(),
                        new BigDecimal(lblCost.getText().replace("$", "")), lblDescription.getText(), 1));
        });

        this.setOnMouseEntered(event -> this.setStyle("-fx-background-color: rgb(210,155,253)"+
                (";-fx-border-color: rgb(15,19,12)")));

        this.setOnMouseExited(event -> this.setStyle("-fx-background-color: rgb(248,248,255)"+
                (";-fx-border-color: rgb(15,19,12)")));


    }

    //For aproximations pane
    public RecordPane(String recordName, BigDecimal cost, String description, MainController mainController, boolean isMaterial){
        this.setHeader(new Label(recordName));
        originalCost = cost;
        currentCost = originalCost; //Initialize
        this.description = description;

        ImageView viewAdd = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/agregar.png")).toExternalForm()));
        viewAdd.setFitWidth(10);
        viewAdd.setFitHeight(10);

        ImageView viewSubtract = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/restar.png")).toExternalForm()));
        viewSubtract.setFitHeight(10);
        viewSubtract.setFitWidth(10);


        btnRemove = new Button();
        btnRemove.setGraphic(viewSubtract);
        btnRemove.setOnAction(event -> {

            int tabIndex = mainController.getTabAproximations().getSelectionModel().getSelectedIndex();
            int currentAmount = Integer.parseInt(txtQuantity.getText());

            currentAmount = currentAmount > 1 ? currentAmount - 1 : 1;

            //If true, searches the index of the selected material to subtract the original cost on the current approximation
            if(currentAmount != 1){

                if(isMaterial){
                    int selectedMaterial = mainController.getAproximations().get(tabIndex).getRecords().indexOf(new Materials(recordName,
                            originalCost, this.description));

                    currentCost = currentCost.subtract(originalCost);

                    mainController.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setUnitCost(currentCost);
                    mainController.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setAmount(currentAmount);
                }
                else{
                    int selectedService = mainController.getAproximations().get(tabIndex).getRecords().indexOf(new Services(recordName,
                            originalCost, this.description));

                    currentCost = currentCost.subtract(originalCost);

                    mainController.getAproximations().get(tabIndex).getRecords().get(selectedService).setCalculateCost(currentCost);
                    mainController.getAproximations().get(tabIndex).getRecords().get(selectedService).setAmount(currentAmount);
                }
            }
            else{
                //When current amount == 1 makes a last calculation and disables the button to avoid negatives

                btnRemove.setDisable(true);

                if(isMaterial){
                    int selectedMaterial = mainController.getAproximations().get(tabIndex).getRecords().indexOf(new Materials(recordName,
                            originalCost, this.description));

                    currentCost = currentCost.subtract(originalCost);

                    mainController.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setCalculateCost(currentCost);
                    mainController.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setAmount(currentAmount);
                }
                else{
                    int selectedService = mainController.getAproximations().get(tabIndex).getRecords().indexOf(new Services(recordName,
                            originalCost, this.description));

                    currentCost = currentCost.subtract(originalCost);

                    mainController.getAproximations().get(tabIndex).getRecords().get(selectedService).setCalculateCost(currentCost);
                    mainController.getAproximations().get(tabIndex).getRecords().get(selectedService).setAmount(currentAmount);
                }
            }
            txtQuantity.setText(String.valueOf(currentAmount));
        });
        btnRemove.setDisable(true);

        txtQuantity = new TextField("1");
        txtQuantity.setMaxSize(50, 10);
        txtQuantity.setFocusTraversable(false);

        btnAdd = new Button();
        btnAdd.setGraphic(viewAdd);
        btnAdd.setOnAction(event -> {
            int tabIndex = mainController.getTabAproximations().getSelectionModel().getSelectedIndex();
            int currentAmount = Integer.parseInt(txtQuantity.getText());

            currentAmount += 1;

            if (btnRemove.isDisable())
                btnRemove.setDisable(false);

            //Same shit as above but for adding the original cost to the selected material

            if(isMaterial){
                int selectedMaterial = mainController.getAproximations().get(tabIndex).getRecords().indexOf(new Materials(recordName,
                        originalCost, this.description));

                currentCost = currentCost.add(originalCost);

                mainController.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setCalculateCost(currentCost);
                mainController.getAproximations().get(tabIndex).getRecords().get(selectedMaterial).setAmount(currentAmount);
            }
            else{
                int selectedService = mainController.getAproximations().get(tabIndex).getRecords().indexOf(new Services(recordName,
                        originalCost, this.description));

                currentCost = currentCost.add(originalCost);

                mainController.getAproximations().get(tabIndex).getRecords().get(selectedService).setCalculateCost(currentCost);
                mainController.getAproximations().get(tabIndex).getRecords().get(selectedService).setAmount(currentAmount);
            }
            txtQuantity.setText(String.valueOf(currentAmount));
        });

        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER_RIGHT);

        flowPane.getChildren().add(btnRemove);
        flowPane.getChildren().add(txtQuantity);
        flowPane.getChildren().add(btnAdd);

        this.setContent(flowPane);
    }

    public RecordPane(String name, int amount){
        this.setHeader(new Label(name));

        Label lblAmount = new Label("Amount: " + amount);
        lblAmount.setAlignment(Pos.BOTTOM_RIGHT);

        this.setContent(lblAmount);
    }


}
