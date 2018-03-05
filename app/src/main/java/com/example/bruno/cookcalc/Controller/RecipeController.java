package com.example.bruno.cookcalc.Controller;


import java.util.List;

public class RecipeController {
    private Integer idRecipe;
    private String name;
    private Double value;
    private List<IngredientController> ingredients;

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

    @Override
    public String toString() {
        return "RecipeController{" +
                "\nidRecipe=" + idRecipe +
                ", \nname='" + name + '\'' +
                ", \nvalue=" + value +
                ", \ningredients=" + ingredients +
                '}';
    }
}
