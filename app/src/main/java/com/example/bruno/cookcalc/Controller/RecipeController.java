package com.example.bruno.cookcalc.Controller;


import java.util.List;

public class RecipeController {
    private Integer idRecipe;
    private Integer minutes;
    private String name;
    private Double portions;
    private Double value;
    private Boolean isBread;
    private Boolean isFavorite;
    private List<IngredientRecipeController> ingredients;
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

    public List<IngredientRecipeController> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientRecipeController> ingredients) {
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

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "RecipeController{" +
                "idRecipe=" + idRecipe +
                ", minutes=" + minutes +
                ", name='" + name + '\'' +
                ", portions=" + portions +
                ", value=" + value +
                ", isBread=" + isBread +
                ", isFavorite=" + isFavorite +
                ", ingredients=" + ingredients +
                ", prices=" + prices +
                '}';
    }
}
