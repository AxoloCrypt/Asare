package com.asare.data;

import java.math.BigDecimal;

public class Services extends Record<Services>{

    public Services() {}

    public Services(String name, BigDecimal unitCost, String description) {
        super(name, unitCost, description);
    }

    public Services(String name, BigDecimal unitCost, String description, int amount){
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

        Services tmpService = new Services(name, unitCost, description);

        for (Services service : super.getRecords()){

            if (tmpService.equals(service)){

                service.setName(newName);
                service.setUnitCost(newUnitCost);
                service.setDescription(newDescription);

                return;
            }

        }
    }

}
