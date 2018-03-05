package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.R;

import java.util.ArrayList;
import java.util.List;

public class ListIngredients extends Activity {

    private ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ingredients);

        list = (ListView) findViewById(R.id.listViewIngredients);
        IngredientModel crud = new IngredientModel(getBaseContext());
        List<IngredientController> ingredients = crud.listIngredients();
        List<String> ingredientsString = new ArrayList<>();

        for (IngredientController ingredient : ingredients){
            String qtd = new Boolean(ingredient.getQuantity() % 1 == 0) ?  ingredient.getQuantity().toString().replace(".0", "") : ingredient.getQuantity().toString();
            String ing = ingredient.getName() + " - R$" + ingredient.getValue() + " (" +
                    qtd
                 //   + ingredient.getQuantity()
                    + " " + ingredient.getUnity() + "(s) )";
            ingredientsString.add(ing);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientsString);
        list.setAdapter(adapter);
    }

    public void returnToMain(View v){
        finish();
//        Intent intent = new Intent (this, MainActivity.class);
//        startActivity(intent);
    }
}
