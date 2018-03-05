package com.example.bruno.cookcalc.Controller;


public class IngredientController {
    private Integer idIngredient;
    private String name;
    private Double value;
    private String unity;
    private Double quantity;

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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
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

    @Override
    public String toString() {
        return "IngredientController{" +
                "\nidIngredient=" + idIngredient +
                ", \nname='" + name + '\'' +
                ", \nvalue=" + value +
                ", \nunity='" + unity + '\'' +
                ", \nquantity=" + quantity +
                '}';
    }
}
