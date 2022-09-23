package com.asare.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Aproximation
{
    private int idAprox;
    private String name;
    private List<Record<?>> records;
    private BigDecimal totalCost;
    private int numberMaterials;
    private int numberServices;
    private LocalDateTime dateCreation;

    public Aproximation(){
        totalCost = new BigDecimal("0.00");
        numberMaterials = 0;
        numberServices = 0;
        records = new ArrayList<>();
    }

    public Aproximation(String name){
        this.name = name;
        totalCost = new BigDecimal("0.00");
        numberMaterials = 0;
        numberServices = 0;
        records = new ArrayList<>();
    }

    public Aproximation(Aproximation aproximation, LocalDateTime dateCreation){
        this.name = aproximation.getName();
        this.records = aproximation.getRecords();
        this.totalCost = aproximation.getTotalCost();
        this.numberMaterials = aproximation.getNumberMaterials();
        this.numberServices = aproximation.getNumberServices();
        this.dateCreation = dateCreation;
    }

    public Aproximation(String name, BigDecimal totalCost, int numberMaterials,
                        int numberServices, LocalDateTime dateCreation){
        this.name = name;
        this.totalCost = totalCost;
        this.numberMaterials = numberMaterials;
        this.numberServices = numberServices;
        this.dateCreation = dateCreation;
        records = new ArrayList<>();
    }

    public Aproximation(int idAprox,String name, BigDecimal totalCost, int numberMaterials,
                        int numberServices, LocalDateTime dateCreation){
        this.idAprox = idAprox;
        this.name = name;
        this.totalCost = totalCost;
        this.numberMaterials = numberMaterials;
        this.numberServices = numberServices;
        this.dateCreation = dateCreation;
        records = new ArrayList<>();
    }



    /*
    @param none
    @return BigDecimal
    Calculates the total of the aproximation and the number of materials and services
    by iterating through records list.
     */
    public BigDecimal calculateTotal(){

        BigDecimal total = new BigDecimal("0.00");
        int numberMaterials = 0, numberServices = 0;

        for (Record<?> record : records) {

            if (record instanceof Materials)
                numberMaterials++;
            else if (record instanceof Services)
                numberServices++;

            total = total.add(record.getCalculateCost());
        }

        setTotalCost(total);
        setNumberMaterials(numberMaterials);
        setNumberServices(numberServices);

        return total;
    }

    //Setter & Getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Record<?>> getRecords() {
        return records;
    }

    public void setRecords(List<Record<?>> records) {
        this.records = records;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getNumberMaterials() {
        return numberMaterials;
    }

    public void setNumberMaterials(int numberMaterials) {
        this.numberMaterials = numberMaterials;
    }

    public int getNumberServices() {
        return numberServices;
    }

    public void setNumberServices(int numberServices) {
        this.numberServices = numberServices;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aproximation that = (Aproximation) o;
        return getNumberMaterials() == that.getNumberMaterials() && getNumberServices() == that.getNumberServices() && getName().equals(that.getName()) && getTotalCost().equals(that.getTotalCost()) && getDateCreation().equals(that.getDateCreation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getTotalCost(), getNumberMaterials(), getNumberServices(), getDateCreation());
    }

    public int getIdAprox() {
        return idAprox;
    }

    public void setIdAprox(int idAprox) {
        this.idAprox = idAprox;
    }
}
