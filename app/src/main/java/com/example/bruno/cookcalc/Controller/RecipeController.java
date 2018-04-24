package com.example.bruno.cookcalc.Controller;


import java.util.List;

public class RecipeController {
    private Integer idRecipe;
    private String name;
    private Double portions;
    private Double value;
    private Boolean isBread;
    private List<IngredientController> ingredients;
    private List<RecipePriceController> prices;

    public RecipeController(){
    }

    public Integer getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(Integer idRecipe) {
        this.idRecipe = idRecipe;
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

    public List<IngredientController> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientController> ingredients) {
        this.ingredients = ingredients;
    }

    public Boolean getBread() {
        return isBread;
    }

    public void setBread(Boolean bread) {
        isBread = bread;
    }

    public Double getPortions() {
        return portions;
    }

    public void setPortions(Double portions) {
        this.portions = portions;
    }

    public List<RecipePriceController> getPrices() {
        return prices;
    }

    public void setPrices(List<RecipePriceController> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "RecipeController{" +
                "idRecipe=" + idRecipe +
                ", name='" + name + '\'' +
                ", portions=" + portions +
                ", value=" + value +
                ", isBread=" + isBread +
                '}';
    }
}
