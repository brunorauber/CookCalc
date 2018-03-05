package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

public class AddRecipe extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe);
    }

    public void saveRecipe(View v){
        RecipeController recipe = new RecipeController();

        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        recipe.setName(text.getText().toString());

        RecipeModel crud = new RecipeModel(getBaseContext());
        crud.insertRecipe(recipe);
//        Intent intent = new Intent (this, MainActivity.class);
//        startActivity(intent);
        finish();
    }

    public void returnToMain(View v){
        finish();
//        Intent intent = new Intent (this, MainActivity.class);
//        startActivity(intent);
    }
}
