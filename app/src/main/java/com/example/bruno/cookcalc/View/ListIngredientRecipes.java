package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.IngredientRecipeController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.IngredientRecipeModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

import java.util.ArrayList;
import java.util.List;

public class ListIngredientRecipes extends Activity {

    private ListView list;
    private Integer recipeId;
    List<IngredientRecipeController> ingredientRecipes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ingredients_recipe);
        Bundle b = getIntent().getExtras();
        recipeId = b.getInt("recipeId");

        list = (ListView) findViewById(R.id.listViewIngredientsRecipe);
        IngredientRecipeModel crud = new IngredientRecipeModel(getBaseContext());
        ingredientRecipes = crud.listIngredientsFromRecipe(recipeId);
        List<String> ingredientRecipesString = new ArrayList<>();

        for (IngredientRecipeController item : ingredientRecipes){
            String rec = item.getIngredientName() + " - R$" + item.getValue() + "(" +
                    item.getQuantity() + " " + item.getUnity() + "[s])";
            System.out.println(rec);
            ingredientRecipesString.add(rec);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientRecipesString);
        list.setAdapter(adapter);

    }

    public void returnToMain(View v){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    public void openIngredientsList(View v){
        Intent intent = new Intent(this, AddIngredientRecipes.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }

    @Override
    public void onResume(){

        list = (ListView) findViewById(R.id.listViewIngredientsRecipe);
        IngredientRecipeModel crud = new IngredientRecipeModel(getBaseContext());
        ingredientRecipes = crud.listIngredientsFromRecipe(recipeId);
        List<String> ingredientRecipesString = new ArrayList<>();

        for (IngredientRecipeController item : ingredientRecipes){
            String rec = item.getIngredientName() + " - R$" + item.getValue() + " (" +
                    item.getQuantity() + " " + item.getUnity() + "[s])";
            System.out.println(rec);
            ingredientRecipesString.add(rec);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientRecipesString);
        list.setAdapter(adapter);
        super.onResume();
    }

}
