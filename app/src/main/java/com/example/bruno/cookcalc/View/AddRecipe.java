package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

public class AddRecipe extends Activity {

    private Integer recipeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe);

        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("recipeId") ) {
            recipeId = b.getInt("recipeId");
            fillEditTextFields(recipeId);
        }
    }

    public void saveRecipe(View v){
        if(recipeId == null){
            insertRecipe(v);
        } else{
            updateRecipe(v);
        }
    }

    public void updateRecipe(View v){
        RecipeController recipeOld = new RecipeModel(getBaseContext()).getRecipeById(recipeId);
        RecipeController recipeNew = new RecipeController();
        recipeNew.setIdRecipe(recipeId);

        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipeNew.setName(text.getText().toString());

        text = (EditText) findViewById(R.id.editTextPortions);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipeNew.setPortions(Double.parseDouble(text.getText().toString()));

        CheckBox isBread = (CheckBox) findViewById(R.id.checkBoxBread);
        recipeNew.setBread(isBread.isChecked());


    }

    public void insertRecipe(View v){
        RecipeController recipe = new RecipeController();

        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipe.setName(text.getText().toString());

        text = (EditText) findViewById(R.id.editTextPortions);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipe.setPortions(Double.parseDouble(text.getText().toString()));

        CheckBox isBread = (CheckBox) findViewById(R.id.checkBoxBread);
        recipe.setBread(isBread.isChecked());

        RecipeModel model = new RecipeModel(getBaseContext());
        model.insertRecipe(recipe);
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



    public void fillEditTextFields(Integer recipeId){
        RecipeController recipe = new RecipeModel(getBaseContext()).getRecipeById(recipeId);
        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        text.setText(recipe.getName());

        text = (EditText) findViewById(R.id.editTextPortions);
        text.setText(recipe.getPortions().toString());

        CheckBox isBread = (CheckBox) findViewById(R.id.checkBoxBread);
        isBread.setChecked(recipe.getBread());


    }



    public void returnToMain(View v){
        finish();
//        Intent intent = new Intent (this, MainActivity.class);
//        startActivity(intent);
    }
}
