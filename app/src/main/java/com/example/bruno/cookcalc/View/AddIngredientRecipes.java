package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

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
    private Integer ingredientId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_recipe);
        Bundle b = getIntent().getExtras();
        recipeId = b.getInt("recipeId");
        IngredientModel model = new IngredientModel(getBaseContext());
        List<IngredientController> ingredients = new ArrayList<>();

        if (b != null && b.containsKey("ingredientId") ) {
            ingredientId = b.getInt("ingredientId");
            ingredients.add(model.selectIngredient(ingredientId));

            IngredientRecipeModel irModel = new IngredientRecipeModel(getBaseContext());
            IngredientRecipeController irController = irModel.getIngredientRecipe(recipeId, ingredientId);
            EditText text = (EditText) findViewById(R.id.editTextValue);
            text.setText(irController.getQuantity().toString());
        }  else {
            ingredients = model.listIngredients();
        }

        spinner = (Spinner) findViewById(R.id.spinnerIngredients);
        List<String> ingredientsString = new ArrayList<>();

        for (IngredientController ingredient : ingredients){
            String unity = ingredient.getUnity();
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
            String qtd = new Boolean(ingredient.getQuantity() % 1 == 0) ?  ingredient.getQuantity().toString().replace(".0", "") : ingredient.getQuantity().toString();
            String ing = ingredient.getName() + " (" + unity + ")";
            ingredientsString.add(ing);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientsString);
        spinner.setAdapter(adapter);
    }

    public void saveIngredientRecipe(View v){
        if(ingredientId == null){
            insertIngredientRecipe(v);
        } else{
            updateIngredientRecipe(v);
        }
    }

    public void updateIngredientRecipe(View v){
        EditText text = (EditText) findViewById(R.id.editTextValue);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }

        IngredientRecipeModel model = new IngredientRecipeModel(getBaseContext());
        IngredientRecipeController irOld = model.getIngredientRecipe(recipeId, ingredientId);
        IngredientRecipeController irNew = new IngredientRecipeController();
        irNew.setIdRecipe(recipeId);
        irNew.setIdIngredient(ingredientId);


        Double ingredientQtd = Double.parseDouble(String.valueOf(text.getText()));
        irNew.setQuantity(ingredientQtd);

        IngredientController ingredient = new IngredientModel(getBaseContext()).selectIngredient(ingredientId);
        Double ingredientValue = ingredient.getLatestValue() * (ingredientQtd / ingredient.getQuantity());
        irNew.setValue(ingredientValue);

        if(!irOld.getQuantity().equals(irNew.getQuantity())){
            model.updateIngredientRecipe(irNew);

            RecipeModel recipeModel = new RecipeModel(getBaseContext());
            RecipeController recipe = recipeModel.getRecipeById(recipeId);
            recipeModel.updateRecipeValue(recipe.getIdRecipe());
        }
        finish();
    }

    public void insertIngredientRecipe(View v){
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
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        Double ingredientQtd = Double.parseDouble(String.valueOf(text.getText()));
        ingredientRecipe.setQuantity(ingredientQtd);

        Double ingredientValue = selectedIngredient.getLatestValue() * (ingredientQtd / selectedIngredient.getQuantity());
        ingredientRecipe.setValue(ingredientValue);

        IngredientRecipeModel ingredientRecipeModel = new IngredientRecipeModel(getBaseContext());
        ingredientRecipeModel.insertIngredientRecipe(ingredientRecipe);
        recipeModel.updateRecipeValue(recipe.getIdRecipe());
        finish();
    }

    public void showErrorMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Preencha todos os campos!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(getBaseContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });

        /*builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getBaseContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });*/
        AlertDialog alerta = builder.create();
        alerta.show();
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
