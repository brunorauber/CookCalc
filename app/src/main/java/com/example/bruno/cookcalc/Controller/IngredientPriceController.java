package com.example.bruno.cookcalc.Controller;

import java.util.Date;


public class IngredientPriceController {
    private Integer idPrice;
    private Integer idIngredient;
    private Date creationDate;
    private Double value;

    public IngredientPriceController() {    }

    public Integer getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(Integer idPrice) {
        this.idPrice = idPrice;
    }

    public Integer getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Integer idIngredient) {
        this.idIngredient = idIngredient;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IngredientPriceController{" +
                "idPrice=" + idPrice +
                ", idIngredient=" + idIngredient +
                ", creationDate=" + creationDate +
                ", value=" + value +
                '}';
    }
}
