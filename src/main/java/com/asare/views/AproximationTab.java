package com.asare.views;

import com.asare.controllers.MainController;
import com.asare.data.Aproximation;
import com.asare.data.Record;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class AproximationTab extends Tab
{
    private SplitPane splitPane;
    private ScrollPane scrollPane;
    private VBox vBoxRecords;
    private VBox vBoxResult;
    private ToolBar toolBar;
    private Button btnSave, btnCalculate;
    private Label lblUsedMaterials, lblUsedServices, lblTotal;

    private String tabname;
    private int tabIndex;

    public AproximationTab(String tabName, MainController mainController){
        super(tabName);
        this.tabname = tabName;


        vBoxRecords = new VBox();

        toolBar = new ToolBar();

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);

        lblUsedMaterials = new Label("Used Materials: ");
        lblUsedServices = new Label("Used Services: ");
        lblTotal = new Label("Total: ");

        vBoxResult = new VBox(25);
        vBoxResult.getChildren().add(lblUsedMaterials);
        vBoxResult.getChildren().add(lblUsedServices);
        vBoxResult.getChildren().add(lblTotal);

        btnCalculate = new Button("Calculate Total");
        //Calculates the total of the current approximation
        btnCalculate.setOnAction(event -> {

            if(btnSave.isDisable())
                btnSave.setDisable(false);

            int tabIndex = mainController.getTabAproximations().getSelectionModel().getSelectedIndex();

            Aproximation aproximation = mainController.getAproximations().get(tabIndex);

            BigDecimal totalCost = aproximation.calculateTotal();

            lblTotal.setText("Total " + totalCost.toString());
            lblUsedMaterials.setText("Used Materials: " + aproximation.getNumberMaterials());
            lblUsedServices.setText("Used Services: " + aproximation.getNumberServices());


        });

        btnSave = new Button("Save");
        btnSave.setAlignment(Pos.BOTTOM_RIGHT);
        btnSave.setDisable(true);
        //Gets the current time and saves the current approximation on history and db
        btnSave.setOnAction(event -> {

            int tabIndex = mainController.getTabAproximations().getSelectionModel().getSelectedIndex();

            Aproximation currentAproximation = mainController.getAproximations().get(tabIndex);
            currentAproximation.setIdAprox(mainController.getConnector().getAproximationRows() + 1);

            Aproximation savedAproximation = new Aproximation(currentAproximation, LocalDateTime.now());

            mainController.getHistory().addToHistory(savedAproximation);
            mainController.getConnector().saveAproximation(savedAproximation, mainController.getUser());

            mainController.getvBoxHistory().getChildren().add(new AproximationPane(savedAproximation.getIdAprox(), savedAproximation.getName(), savedAproximation.getDateCreation(), mainController));
        });

        toolBar.getItems().add(btnSave);
        toolBar.getItems().add(btnCalculate);

        vBoxResult.getChildren().add(toolBar);

        scrollPane.setContent(vBoxRecords);

        splitPane = new SplitPane(scrollPane, vBoxResult);

        this.setContent(splitPane);

        this.setOnClosed(event -> {
            int currentIndex = mainController.getTabAproximations().getSelectionModel().getSelectedIndex();

            mainController.getAproximations().remove(currentIndex + 1);

        });

    }

    public AproximationTab(String tabName, int numberMaterials, int numberServices, String totalCost, List<Record<?>> savedrecords, MainController mainController){
        super(tabName);

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);

        vBoxRecords = new VBox();

        vBoxResult = new VBox(25);

        lblUsedMaterials = new Label("Used Materials: " +  numberMaterials);
        lblUsedServices = new Label("Used Services: " + numberServices);
        lblTotal = new Label("Total: " + totalCost);

        vBoxResult.getChildren().add(lblUsedMaterials);
        vBoxResult.getChildren().add(lblUsedServices);
        vBoxResult.getChildren().add(lblTotal);

        for (Record<?> record : savedrecords){
            vBoxRecords.getChildren().add(new RecordPane(record.getName(), record.getAmount()));
        }

        scrollPane.setContent(vBoxRecords);

        splitPane = new SplitPane(vBoxRecords, vBoxResult);


        this.setContent(splitPane);

        this.setOnClosed(event -> {
            int currentIndex = mainController.getTabAproximations().getSelectionModel().getSelectedIndex();

            mainController.getAproximations().remove(currentIndex + 1);

        });
    }

    public SplitPane getSplitPane() {
        return splitPane;
    }

    public void setSplitPane(SplitPane splitPane) {
        this.splitPane = splitPane;
    }

    public VBox getvBoxRecords() {
        return vBoxRecords;
    }

    public void setvBoxRecords(VBox vBoxRecords) {
        this.vBoxRecords = vBoxRecords;
    }
}
