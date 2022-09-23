package com.asare.enums;

public enum Navigator {

    MAIN("/application/app.fxml");
    public String path;

    Navigator(String path){
        this.path = path;
    }


}
