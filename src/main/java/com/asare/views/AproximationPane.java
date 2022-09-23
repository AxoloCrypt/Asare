package com.asare.views;


import com.asare.controllers.MainController;
import com.asare.data.Aproximation;
import javafx.geometry.Pos;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AproximationPane extends DialogPane {

    private final LocalDateTime dateCreation;
    private final int idAprox;


    public AproximationPane(int idAprox, String name, LocalDateTime dateCreation, MainController controller) {
        this.setStyle("-fx-border-color: rgb(15,19,12);" + "-fx-background-color: rgb(248, 248, 255)");
        this.dateCreation = dateCreation;
        this.idAprox = idAprox;

        Label lblName = new Label(name);

        Label lblDate = new Label(DateTimeFormatter.ISO_LOCAL_DATE.format(this.dateCreation));
        lblDate.setAlignment(Pos.CENTER_RIGHT);

        this.setHeader(lblName);
        this.setContent(lblDate);

        this.setOnMouseClicked(event -> {

            for (Aproximation aproximation : controller.getHistory().getSavedAproximations()){

                if (aproximation.getDateCreation().isEqual(this.dateCreation) && aproximation.getIdAprox() == idAprox){

                    AproximationTab aproximationTab = new AproximationTab(aproximation.getName(),
                            aproximation.getNumberMaterials(), aproximation.getNumberServices(),aproximation.getTotalCost().toString(), aproximation.getRecords(), controller);

                    controller.getTabAproximations().getTabs().add(aproximationTab);
                    controller.getAproximations().add(new Aproximation());
                }

            }

        });
        this.setOnMouseEntered(event -> {
            this.setStyle("-fx-background-color: rgb(210,155,253)"+
                    (";-fx-border-color: rgb(15,19,12)"));

        });
        this.setOnMouseExited(event -> {
            this.setStyle("-fx-background-color: rgb(248,248,255)"+
                    (";-fx-border-color: rgb(15,19,12)"));
        });

    }

}
