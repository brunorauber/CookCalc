package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bruno.cookcalc.Controller.IngredientRecipeController;
import com.example.bruno.cookcalc.Model.IngredientRecipeModel;
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
            ingredientRecipesString.add(rec);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientRecipesString);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openIngredientDetails(view, ingredientRecipes.get(position) );
            }
        });

    }

    public void openRecipeHistory(View v){
        Intent intent = new Intent(this, ListRecipePrices.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }
    public void openIngredientDetails(View v, IngredientRecipeController ingredient){
        Intent intent = new Intent(this, AddIngredientRecipes.class);
        intent.putExtra("ingredientId", ingredient.getIdIngredient());
        intent.putExtra("recipeId", ingredient.getIdRecipe());
        startActivity(intent);
    }

    public void returnToMain(View v){
        finish();
    }

    public void openIngredientsList(View v){
        Intent intent = new Intent(this, AddIngredientRecipes.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }
    public void editRecipe(View v){
        Intent intent = new Intent(this, AddRecipe.class);
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
            String unity = item.getUnity();
            switch (unity) {
                case "Kilograma":
                    unity = "kg";
                    break;
                case "Grama":
                    unity = "g";
                    break;
                case "Litro":
                    unity = "l";
                    break;
                case "Mililitro":
                    unity = "ml";
                    break;
                case "Unidade":
                    unity = "un";
                    break;
            }

            String rec = item.getIngredientName() + " - R$" + item.getValue() + " (" +
                    item.getQuantity() + " " + unity + ")";
            ingredientRecipesString.add(rec);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientRecipesString);
        list.setAdapter(adapter);
        super.onResume();
    }

}
