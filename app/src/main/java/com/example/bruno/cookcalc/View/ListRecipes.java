package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

import java.util.ArrayList;
import java.util.List;

public class ListRecipes extends Activity {

    private ListView list;
    List<RecipeController> recipes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recipes);

        list = (ListView) findViewById(R.id.listViewRecipes);
        RecipeModel crud = new RecipeModel(getBaseContext());
        recipes = crud.listRecipes();
        List<String> recipesString = new ArrayList<>();

        for (RecipeController recipe : recipes){
            String rec = recipe.getName() + " - R$" + recipe.getValue();
            recipesString.add(rec);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipesString);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openIngredients(view, recipes.get(position) );
            }
        });
    }

    public void returnToMain(View v){
        finish();
    }

    public void openIngredients(View v, RecipeController recipe){
        Intent intent = new Intent(this, ListIngredientRecipes.class);
        intent.putExtra("recipeId", recipe.getIdRecipe());
        startActivity(intent);
    }

    @Override
    public void onResume(){
        list = (ListView) findViewById(R.id.listViewRecipes);
        RecipeModel crud = new RecipeModel(getBaseContext());
        recipes = crud.listRecipes();
        List<String> recipesString = new ArrayList<>();

        for (RecipeController recipe : recipes){
            String rec = recipe.getName() + " - R$" + recipe.getValue();
            recipesString.add(rec);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipesString);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openIngredients(view, recipes.get(position) );
            }
        });

        super.onResume();
    }
}
