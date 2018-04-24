package com.example.bruno.cookcalc.Controller;

import java.util.Date;


public class RecipePriceController {
    private Integer idPrice;
    private Integer idRecipe;
    private Date creationDate;
    private Double value;

    public RecipePriceController() {    }

    public Integer getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(Integer idPrice) {
        this.idPrice = idPrice;
    }

    public Integer getidRecipe() {
        return idRecipe;
    }

    public void setidRecipe(Integer idRecipe) {
        this.idRecipe = idRecipe;
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
                ", idRecipe=" + idRecipe +
                ", creationDate=" + creationDate +
                ", value=" + value +
                '}';
    }
}
