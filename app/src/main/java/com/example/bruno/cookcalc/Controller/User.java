package com.example.bruno.cookcalc.Controller;

import java.util.List;

public class User {

    private String email;
    private List<RecipeController> recipes;
    private List<IngredientController> ingredients;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RecipeController> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeController> recipes) {
        this.recipes = recipes;
    }

    public List<IngredientController> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientController> ingredients) {
        this.ingredients = ingredients;
    }
}
