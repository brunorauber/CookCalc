package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bruno.cookcalc.Controller.IngredientRecipeController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.IngredientRecipeModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListRecipes extends AppCompatActivity {

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

        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                            ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Menu.NONE, 1, Menu.NONE, "Remover");
            }
        });
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        Integer position = menuInfo.position;
        RecipeController recipeController = recipes.get(position);
        RecipeModel irModel =  new RecipeModel(getBaseContext());

        switch (item.getItemId()) {
            case 1:
                irModel.removeRecipe(recipeController.getIdRecipe());
                onResume();
                Toast.makeText(getBaseContext(), "Item Removido", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void returnToMain(View v){
        finish();
    }

    public void openIngredients(View v, RecipeController recipe){
        Intent intent = new Intent(this, ListIngredientRecipes.class);
        intent.putExtra("recipeId", recipe.getIdRecipe());
        intent.putExtra("origin", "listRecipes");
        startActivity(intent);
    }

    @Override
    public void onResume(){
        list = (ListView) findViewById(R.id.listViewRecipes);
        RecipeModel crud = new RecipeModel(getBaseContext());
        recipes = crud.listRecipes();
        List<String> recipesString = new ArrayList<>();

        for (RecipeController recipe : recipes){
            Double value = recipe.getValue();
            DecimalFormat numberFormat;
            if(value < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }
            String valor = "R$ " + numberFormat.format(value);

            String rec = recipe.getName() + " - " + valor;
            if(recipe.getFavorite()){
//                rec = "✪ " + rec;
                rec = "★ " + rec;
            }
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
        Toast.makeText(getBaseContext(), "Para detalhes, clique no nome da receita", Toast.LENGTH_LONG).show();
        super.onResume();
    }

    public void viewAddRecipe(View v){
        Intent intent = new Intent (this, AddRecipe.class);
        startActivity(intent);
    }
}
