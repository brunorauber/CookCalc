package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.IngredientRecipeController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.IngredientRecipeModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

import java.util.ArrayList;
import java.util.List;

public class AddIngredientRecipes extends Activity {

    private Spinner spinner;
    private Integer recipeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_recipe);
        Bundle b = getIntent().getExtras();
        recipeId = b.getInt("recipeId");

        spinner = (Spinner) findViewById(R.id.spinnerIngredients);
        IngredientModel crud = new IngredientModel(getBaseContext());
        List<IngredientController> ingredients = crud.listIngredients();
        List<String> ingredientsString = new ArrayList<>();

        for (IngredientController ingredient : ingredients){
            String qtd = new Boolean(ingredient.getQuantity() % 1 == 0) ?  ingredient.getQuantity().toString().replace(".0", "") : ingredient.getQuantity().toString();
            String ing = ingredient.getName() + " (" + ingredient.getUnity() + "(s) )";
            ingredientsString.add(ing);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientsString);
        spinner.setAdapter(adapter);


    }

    public void saveIngredientRecipe(View v){
        IngredientRecipeController ingredientRecipe = new IngredientRecipeController();

        spinner = (Spinner) findViewById(R.id.spinnerIngredients);

        IngredientModel ingredientModel = new IngredientModel(getBaseContext());
        List<IngredientController> ingredients = ingredientModel .listIngredients();
        Integer position = spinner.getSelectedItemPosition();
        IngredientController selectedIngredient = ingredients.get(position);
        ingredientRecipe.setIdIngredient(selectedIngredient.getIdIngredient());
        ingredientRecipe.setIngredientName(selectedIngredient.getName());

        RecipeModel recipeModel = new RecipeModel(getBaseContext());
        RecipeController recipe = recipeModel.getRecipeById(recipeId);
        ingredientRecipe.setIdRecipe(recipe.getIdRecipe());

        EditText text = (EditText) findViewById(R.id.editTextValue);
        Double ingredientQtd= Double.parseDouble(String.valueOf(text.getText()));
        ingredientRecipe.setQuantity(ingredientQtd);

        Double ingredientValue = selectedIngredient.getValue() * (ingredientQtd / selectedIngredient.getQuantity());
        ingredientRecipe.setValue(ingredientValue);

        IngredientRecipeModel ingredientRecipeModel = new IngredientRecipeModel(getBaseContext());
        ingredientRecipeModel.insertIngredientRecipe(ingredientRecipe);
        recipeModel.updateRecipeValue(recipe.getIdRecipe());
        finish();
//        System.out.println("selectedIngredient:" + selectedIngredient);
//        System.out.println("recipe:" + recipe);


//        IngredientModel crud = new IngredientModel(getBaseContext());
//        crud.insertIngredient(ingredientRecipe);
//        Intent intent = new Intent (this, MainActivity.class);
//        startActivity(intent);
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
}
