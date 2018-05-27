package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.IngredientRecipeController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Controller.User;
import com.example.bruno.cookcalc.Model.CloudModel;
import com.example.bruno.cookcalc.Model.ConfigModel;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.IngredientRecipeModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {
    private CloudModel cloudModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        IngredientModel ingredientModel = new IngredientModel(getBaseContext());
        RecipeModel recipeModel = new RecipeModel(getBaseContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //sync();
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

    public void viewConfig(View v){
        Intent intent = new Intent (this, ConfigView.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }

    public void sync(View v){
        ConfigModel configModel = new ConfigModel(getBaseContext());
        String email = null;
        if (configModel.configExists("email")){
            email = configModel.getConfigValue("email");
        }
        if(email == null || email.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há um email configurado", Toast.LENGTH_SHORT).show();
            return;
        }

        String state = null;
        if (configModel.configExists("state")){
            state = configModel.getConfigValue("state");
        }
        if(state == null || state.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há um estado configurado", Toast.LENGTH_SHORT).show();
            return;
        }

        String city = null;
        if (configModel.configExists("city")){
            city = configModel.getConfigValue("city");
        }
        if(city == null || city.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há uma cidade configurada", Toast.LENGTH_SHORT).show();
            return;
        }

        IngredientModel ingredientModel= new IngredientModel(getBaseContext());
        List<IngredientController> ingredients = ingredientModel.listIngredients();

        RecipeModel recipeModel = new RecipeModel(getBaseContext());
        List<RecipeController> recipes = recipeModel.listRecipes();

        IngredientRecipeModel ingredientRecipeModel = new IngredientRecipeModel(getBaseContext());
        for(RecipeController recipe : recipes){
            List<IngredientRecipeController> ingredientRecipes = ingredientRecipeModel.listIngredientsFromRecipe(recipe.getIdRecipe());
            recipe.setIngredients(ingredientRecipes);
        }

        cloudModel = new CloudModel();
        cloudModel.writeNewUser(email, state, city, recipes, ingredients);
        cloudModel.getSync(state, city);
        System.out.println("QWERTYU");
        System.out.println("QWERTYU");
        System.out.println("QWERTYU");
        System.out.println("QWERTYU");
        //System.out.println(cloudModel.getData());
    }
    /*
    public void sync(){
        ConfigModel configModel = new ConfigModel(getBaseContext());
        String email = null;
        if (configModel.configExists("email")){
            email = configModel.getConfigValue("email");
        }

        if(email == null || email.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há um email configurado", Toast.LENGTH_SHORT).show();
        }
        String state = null;
        if (configModel.configExists("state")){
            state = configModel.getConfigValue("state");
        }

        if(state == null || state.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há um estado configurado", Toast.LENGTH_SHORT).show();
        }
        String city = null;
        if (configModel.configExists("city")){
            city = configModel.getConfigValue("city");
        }

        if(city == null || city.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há uma cidade configurada", Toast.LENGTH_SHORT).show();
        }

        IngredientModel ingredientModel= new IngredientModel(getBaseContext());
        List<IngredientController> ingredients = ingredientModel.listIngredients();

        RecipeModel recipeModel = new RecipeModel(getBaseContext());
        List<RecipeController> recipes = recipeModel.listRecipes();

        IngredientRecipeModel ingredientRecipeModel = new IngredientRecipeModel(getBaseContext());
        for(RecipeController recipe : recipes){
            List<IngredientRecipeController> ingredientRecipes = ingredientRecipeModel.listIngredientsFromRecipe(recipe.getIdRecipe());
            recipe.setIngredients(ingredientRecipes);
        }

        cloudModel = new CloudModel();
        cloudModel.writeNewUser(email, state, city, recipes, ingredients);
//        cloudModel.getSync();
    }
    */
}