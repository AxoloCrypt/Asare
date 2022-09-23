package com.asare.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    @Value("classpath:/application/login.fxml")
    private Resource loginResource;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(loginResource.getURL());
            Parent parent = loader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent));
            stage.show();

        }catch (IOException e){
            throw new RuntimeException(e);
        }


    }
}
