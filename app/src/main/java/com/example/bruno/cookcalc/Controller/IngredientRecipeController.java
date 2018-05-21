package com.example.bruno.cookcalc.Controller;


public class IngredientRecipeController {
    private Integer idIngredient;
    private Integer idRecipe;
    private String ingredientName;
    private String ingredientBrand;
    private String unity;
    private Double value;
    private Double quantity;
    private Boolean isBreadBase;

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

    public Boolean getBreadBase() {
        return isBreadBase;
    }

    public void setBreadBase(Boolean breadBase) {
        isBreadBase = breadBase;
    }

    public String getIngredientBrand() {
        return ingredientBrand;
    }

    public void setIngredientBrand(String ingredientBrand) {
        this.ingredientBrand = ingredientBrand;
    }

    @Override
    public String toString() {
        return "IngredientRecipeController{" +
                "idIngredient=" + idIngredient +
                ", idRecipe=" + idRecipe +
                ", ingredientName='" + ingredientName + '\'' +
                ", ingredientBrand='" + ingredientBrand + '\'' +
                ", unity='" + unity + '\'' +
                ", value=" + value +
                ", quantity=" + quantity +
                ", isBreadBase=" + isBreadBase +
                '}';
    }
}
