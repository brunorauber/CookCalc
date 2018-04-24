package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.IngredientRecipeController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.IngredientRecipeModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

import java.util.List;

public class AddIngredient extends Activity {

    private Integer ingredientId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient);

        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("ingredientId") ) {
            ingredientId = b.getInt("ingredientId");
            fillEditTextFields(ingredientId);
        }
    }

    public void saveIngredient(View v){
        if(ingredientId == null){
            insertIngredient(v);
        } else{
            updateIngredient(v);
        }
    }


    public void updateIngredient(View v){
        IngredientController ingredientOld = new IngredientModel(getBaseContext()).selectIngredient(ingredientId);
        IngredientController ingredientNew = new IngredientController();
        ingredientNew.setIdIngredient(ingredientId);

        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        ingredientNew.setName(text.getText().toString());

        text = (EditText) findViewById(R.id.editTextBrand);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        ingredientNew.setBrand(text.getText().toString());

        text = (EditText) findViewById(R.id.editTextValue);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        ingredientNew.setLatestValue(Double.parseDouble(text.getText().toString()));

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupUnity);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            showErrorMessage();
            return;
        } else{
            if (checkedRadioButtonId == R.id.radioButtonKg) {
                ingredientNew.setUnity("Kilograma");
            } else if (checkedRadioButtonId == R.id.radioButtonG) {
                ingredientNew.setUnity("Grama");
            } else if (checkedRadioButtonId == R.id.radioButtonL) {
                ingredientNew.setUnity("Litro");
            } else if (checkedRadioButtonId == R.id.radioButtonMl) {
                ingredientNew.setUnity("Mililitro");
            } else if (checkedRadioButtonId == R.id.radioButtonUn) {
                ingredientNew.setUnity("Unidade");
            }
        }

        text = (EditText) findViewById(R.id.editTextQuantity);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        ingredientNew.setQuantity(Double.parseDouble(text.getText().toString()));

        if(!ingredientOld.getName().equals(ingredientNew.getName())
                || !ingredientOld.getBrand().equals(ingredientNew.getBrand())
                || !ingredientOld.getLatestValue().equals(ingredientNew.getLatestValue())
                || !ingredientOld.getUnity().equals(ingredientNew.getUnity())
                || !ingredientOld.getQuantity().equals(ingredientNew.getQuantity())
                ){
            IngredientModel model = new IngredientModel(getBaseContext());
            model.updateIngredient(ingredientNew);
        }

        if(!ingredientOld.getLatestValue().equals(ingredientNew.getLatestValue())){
            IngredientRecipeModel irModel = new IngredientRecipeModel(getBaseContext());
            RecipeModel recipeModel = new RecipeModel(getBaseContext());
            List<IngredientRecipeController> ingredientRecipeList = irModel.getIngredientsById(ingredientNew.getIdIngredient());
            for(IngredientRecipeController ingredientRecipe : ingredientRecipeList){
                Double ingredientQtd = ingredientRecipe.getQuantity();
                Double ingredientValue = ingredientNew.getLatestValue() * (ingredientQtd / ingredientNew.getQuantity());
                irModel.updateIngredientRecipeValue(ingredientRecipe.getIdIngredient(),
                        ingredientRecipe.getIdRecipe(), ingredientValue);
                recipeModel.updateRecipeValue(ingredientRecipe.getIdRecipe());
            }


        }
        finish();

    }

    public void insertIngredient(View v){
        IngredientController ingredient = new IngredientController();

        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        ingredient.setName(text.getText().toString());

        text = (EditText) findViewById(R.id.editTextBrand);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        ingredient.setBrand(text.getText().toString());

        text = (EditText) findViewById(R.id.editTextValue);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        ingredient.setLatestValue(Double.parseDouble(text.getText().toString()));

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupUnity);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            showErrorMessage();
            return;
        } else{
            if (checkedRadioButtonId == R.id.radioButtonKg) {
                ingredient.setUnity("Kilograma");
            } else if (checkedRadioButtonId == R.id.radioButtonG) {
                ingredient.setUnity("Grama");
            } else if (checkedRadioButtonId == R.id.radioButtonL) {
                ingredient.setUnity("Litro");
            } else if (checkedRadioButtonId == R.id.radioButtonMl) {
                ingredient.setUnity("Mililitro");
            } else if (checkedRadioButtonId == R.id.radioButtonUn) {
                ingredient.setUnity("Unidade");
            }
        }

        text = (EditText) findViewById(R.id.editTextQuantity);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        ingredient.setQuantity(Double.parseDouble(text.getText().toString()));

        IngredientModel model = new IngredientModel(getBaseContext());
        model.insertIngredient(ingredient);
        finish();
        Intent intent = new Intent (this, ListIngredients.class);
        startActivity(intent);

    }

    public void showErrorMessage(){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Aviso");
        //define a mensagem
        builder.setMessage("Preencha todos os campos!");
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(getBaseContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        //define um botão como negativo.
        /*builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getBaseContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });*/
        //cria o AlertDialog
        AlertDialog alerta = builder.create();
        //Exibe
        alerta.show();
    }

    public void fillEditTextFields(Integer ingredientId){
        IngredientController ingredient = new IngredientModel(getBaseContext()).selectIngredient(ingredientId);
        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        text.setText(ingredient.getName());

        text = (EditText) findViewById(R.id.editTextBrand);
        text.setText(ingredient.getBrand());

        text = (EditText) findViewById(R.id.editTextValue);
        text.setText(ingredient.getLatestValue().toString());

        text = (EditText) findViewById(R.id.editTextQuantity);
        text.setText(ingredient.getQuantity().toString());

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupUnity);
        String unity = ingredient.getUnity();
        switch (unity) {
            case "Kilograma":
                radioGroup.check (R.id.radioButtonKg);
                break;
            case "Grama":
                radioGroup.check(R.id.radioButtonG);
                break;
            case "Litro":
                radioGroup.check(R.id.radioButtonL);
                break;
            case "Mililitro":
                radioGroup.check(R.id.radioButtonMl);
                break;
            case "Unidade":
                radioGroup.check(R.id.radioButtonUn);
                break;
        }
    }

    public void returnToMain(View v){
        finish();
    }
}
