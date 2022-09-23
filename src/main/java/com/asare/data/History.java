package com.asare.data;

import java.util.ArrayList;
import java.util.List;

public class History
{
    private List<Aproximation> savedAproximations;

    public History() {
        savedAproximations = new ArrayList<>(); }

    public History(List<Aproximation> savedAproximations){
        this.savedAproximations = savedAproximations;
    }

    public void addToHistory(Aproximation aproximation){
        savedAproximations.add(aproximation);
    }

    /*
    @param aproximation
    @return
    Deletes passed aproximation from history.
     */
    public void deleteAproximation(Aproximation aproximation){
        savedAproximations.remove(aproximation);
    }

    //TODO
    // Implementation needed
    public void exportPDF(){

    }

    public List<Aproximation> getSavedAproximations() {
        return savedAproximations;
    }

    public void setSavedAproximations(List<Aproximation> savedAproximations) {
        this.savedAproximations = savedAproximations;
    }
}
