package com.example.myapplication.Model;


public class Costumer {
    public int idCostumer;
    public String costumerName;
    public int idLocation;


    public Costumer(int idCostumer,String costumerName, int idLocation, int idTool){
        this.idCostumer=idCostumer;
        this.costumerName=costumerName;
        this.idLocation=idLocation;

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


}
