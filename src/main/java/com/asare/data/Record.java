package com.asare.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Record <T>
{
    private String name;
    private BigDecimal unitCost;

    private BigDecimal calculateCost;
    private String description;
    private List<T> records;
    private int amount;


    public Record(String name, BigDecimal unitCost, String description)
    {
        this.name = name;
        this.unitCost = unitCost;
        this.calculateCost = unitCost;
        this.description = description;
        records = new ArrayList<>();
    }

    public Record(String name, BigDecimal unitCost, String description, int amount){
        this.name = name;
        this.unitCost = unitCost;
        this.calculateCost = unitCost;
        this.description = description;
        this.amount = amount;
        records = new ArrayList<>();
    }

    public Record() {
        records = new ArrayList<>();
    }

    /*
    @param record
    @return boolean
    @throws NullPointerException
    Adds a record to the records list, if record == null an exception is thrown.
     */
    public boolean addRecord(T record) throws NullPointerException{

        if (record == null)
            throw new NullPointerException();

        return records.add(record);
    }

    /*
    @param record
    @return boolean
    Returns true if the searched record is on the list and removed,
    else return false
     */
    public boolean deleteRecord(T record){

        return records.remove(record);
    }

    //TODO
    // Needs to be developed
    public void createCopy(T record){
        records.add(record);
    }

    //Implementation in children
    public abstract void editRecord(String name, BigDecimal unitCost, String description, String newName, BigDecimal newUnitCost, String newDescription);


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record<?> record = (Record<?>) o;
        return getName().equals(record.getName()) && getUnitCost().equals(record.getUnitCost()) && getDescription().equals(record.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getUnitCost(), getDescription());
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getCalculateCost() {
        return calculateCost;
    }

    public void setCalculateCost(BigDecimal calculateCost) {
        this.calculateCost = calculateCost;
    }
}
