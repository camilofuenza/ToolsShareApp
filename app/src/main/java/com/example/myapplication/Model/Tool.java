package com.example.myapplication.Model;

public class Tool {

    private int idTool;
    private String toolName;
    private String toolDescription;
    private int isAvailable;
    private int idCategory;
    private int idCostumer;
    private String costumerName;
    private String categoryName;


    public Tool(int idTool, String toolName, String toolDescription, int isAvailable, int idCategory, int idCostumer
    ,String costumerName,String categoryName) {
        this.idTool = idTool;
        this.toolName = toolName;
        this.toolDescription = toolDescription;
        this.isAvailable = isAvailable;
        this.idCategory = idCategory;
        this.idCostumer=idCostumer;
        this.costumerName=costumerName;
        this.categoryName=categoryName;
    }

    public int getIdTool() {
        return idTool;
    }

    public void setIdTool(int idTool) {
        this.idTool = idTool;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolDescription() {
        return toolDescription;
    }

    public void setToolDescription(String toolDescription) {
        this.toolDescription = toolDescription;
    }

    public int getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
