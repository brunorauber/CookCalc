package com.example.bruno.cookcalc.Controller;

import java.util.List;

public class User {

    private String email;
    private String state;
    private String city;
    private List<RecipeController> recipes;
    private List<IngredientController> ingredients;

    public User() {
    }

    public User(String email, String state, String city, List<RecipeController> recipes, List<IngredientController> ingredients) {
        this.email = email;
        this.state = state;
        this.city = city;
        this.recipes = recipes;
        this.ingredients = ingredients;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public User(String email) {
        this.email = email;
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
