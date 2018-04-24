package com.example.bruno.cookcalc.Controller;


import java.util.Date;

public class IngredientController {
    private Integer idIngredient;
    private String name;
    private String brand;
    private Double latestValue;
    private String unity;
    private Double quantity;
    private Date lastUpdate;

    public IngredientController(){
    }

    public Integer getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Integer idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatestValue() {
        return latestValue;
    }

    public void setLatestValue(Double latestValue) {
        this.latestValue = latestValue;
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "IngredientController{" +
                "idIngredient=" + idIngredient +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", latestValue=" + latestValue +
                ", unity='" + unity + '\'' +
                ", quantity=" + quantity +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
