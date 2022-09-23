package com.asare.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class AsareApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void start(Stage primaryStage) throws Exception {
        applicationContext.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception{
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void init() throws Exception{
        applicationContext = new SpringApplicationBuilder(MainApplication.class).run();
    }


}
