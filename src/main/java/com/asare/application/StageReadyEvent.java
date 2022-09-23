package com.asare.application;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class StageReadyEvent extends ApplicationEvent {

    public StageReadyEvent(Stage primaryStage) {
        super(primaryStage);
    }

    public Stage getStage() {
        return (Stage) getSource();
    }
}
