package com.asare.data;

import java.math.BigDecimal;

public class Materials extends Record<Materials>{

    public Materials() {}

    public Materials(String name, BigDecimal unitCost, String description) {
        super(name, unitCost, description);
    }

    public Materials(String name, BigDecimal unitCost, String description, int amount){
        super(name, unitCost, description, amount);
    }

    /*
    @param name, unitCost, description, newName, newUnitCost, newDescription
    @return
    Searches for the selected record, if it's found, the record data will be replaced by the
    new data passed in the parameters and quits the method.
     */
    @Override
    public void editRecord(String name, BigDecimal unitCost, String description, String newName, BigDecimal newUnitCost, String newDescription) {

        Materials tmpMaterial = new Materials(name, unitCost, description);

        for (Materials material : super.getRecords()){

            if (tmpMaterial.equals(material)){
                material.setName(newName);
                material.setUnitCost(newUnitCost);
                material.setDescription(newDescription);

                return;
            }
        }
    }

}
