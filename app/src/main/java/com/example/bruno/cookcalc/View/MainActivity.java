package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        IngredientModel ingredientModel = new IngredientModel(getBaseContext());
        RecipeModel recipeModel = new RecipeModel(getBaseContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void viewAddIngredient(View v){
        Intent intent = new Intent (this, AddIngredient.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }

    public void viewListIngredient(View v){
        Intent intent = new Intent (this, ListIngredients.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }
    public void viewAddRecipe(View v){
        Intent intent = new Intent (this, AddRecipe.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }

    public void viewListRecipes(View v){
        Intent intent = new Intent (this, ListRecipes.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }

    public void sync(View v){



    }

}