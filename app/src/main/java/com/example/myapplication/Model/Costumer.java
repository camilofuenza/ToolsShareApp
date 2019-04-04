package com.example.myapplication.Model;


public class Costumer {
    public int idCostumer;
    public String costumerName;
    public int idLocation;

    public String postalCode;


    public Costumer(int idCostumer,String costumerName, int idLocation, String postalCode){
        this.idCostumer=idCostumer;
        this.costumerName=costumerName;
        this.idLocation=idLocation;

        this.postalCode = postalCode;

    }

    public int getIdCostumer() {
        return idCostumer;
    }

    public void setIdCostumer(int idCostumer) {
        this.idCostumer = idCostumer;
    }

    public String getCostumerName() {
        return costumerName;
    }

    public void setCostumerName(String costumerName) {
        this.costumerName = costumerName;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
