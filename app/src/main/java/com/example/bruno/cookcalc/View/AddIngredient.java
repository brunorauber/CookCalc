package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.R;

public class AddIngredient extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient);
    }

    public void saveIngredient(View v){
        IngredientController ingredient = new IngredientController();

        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        ingredient.setName(text.getText().toString());

        text = (EditText) findViewById(R.id.editTextValue);
        ingredient.setValue(Double.parseDouble(text.getText().toString()));

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupUnity);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == -1) {
            // No item selected
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
        ingredient.setQuantity(Double.parseDouble(text.getText().toString()));

        IngredientModel crud = new IngredientModel(getBaseContext());
        crud.insertIngredient(ingredient);
        finish();
//        Intent intent = new Intent (this, MainActivity.class);
//        startActivity(intent);
    }

    public void returnToMain(View v){
        finish();
//        Intent intent = new Intent (this, MainActivity.class);
//        startActivity(intent);
    }
}
