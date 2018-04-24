package com.example.bruno.cookcalc.Controller;


public class IngredientRecipeController {
    private Integer idIngredient;
    private Integer idRecipe;
    private String ingredientName;
    private String unity;
    private Double value;
    private Double quantity;

    public IngredientRecipeController(){
    }

    public String getUnity() {
        return unity;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }

    public Integer getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(Integer idIngredient) {
        this.idIngredient = idIngredient;
    }

    public Integer getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(Integer idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "IngredientRecipeController{" +
                "idIngredient=" + idIngredient +
                ", idRecipe=" + idRecipe +
                ", ingredientName='" + ingredientName + '\'' +
                ", unity='" + unity + '\'' +
                ", value=" + value +
                ", quantity=" + quantity +
                '}';
    }
}
