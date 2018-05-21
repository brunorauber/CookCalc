package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListIngredients extends Activity {

    private ListView list;
    private List<IngredientController> ingredients;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ingredients);

        list = (ListView) findViewById(R.id.listViewIngredients);
        IngredientModel crud = new IngredientModel(getBaseContext());
        ingredients = crud.listIngredients();
        List<String> ingredientsString = new ArrayList<>();
        List<String> ingredientsUpdate = new ArrayList<>();
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> datum = new HashMap<String, String>();

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

            Double value = ingredient.getLatestValue();
            DecimalFormat numberFormat;
            if(value < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }
            String valor = "R$ " + numberFormat.format(value);

            String ing = ingredient.getName() + " " + ingredient.getBrand() + " - " + valor + " (" +
                    qtd + " " + unity + ")";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", new Locale("pt_BR"));
            String dataFormatada = sdf.format(ingredient.getLastUpdate());
            String lastUpdate = "Última edição em: " + dataFormatada;

            ingredientsString.add(ing);
            ingredientsUpdate.add(ingredient.getLastUpdate().toString());

            datum = new HashMap<String, String>();
            datum.put( "line1", ing);
            datum.put( "line2", lastUpdate );
            data.add( datum );
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"line1", "line2" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openIngredientDetails(view, ingredients.get(position) );
            }
        });

        list.setAdapter(adapter);

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
        IngredientController ingredientController = ingredients.get(position);
        IngredientModel ingModel =  new IngredientModel(getBaseContext());

        switch (item.getItemId()) {
            case 1:
                if(!ingModel.isIngredientOnRecipe(ingredientController.getIdIngredient())){
                    ingModel.removeIngredient(ingredientController.getIdIngredient());
                    Toast.makeText(getBaseContext(), "Item Removido", Toast.LENGTH_SHORT).show();
                    onResume();
                } else{
                    Toast.makeText(getBaseContext(), "Item não pode ser removido! Há receitas contendo este ingrediente!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void openIngredientDetails(View v, IngredientController ingredient){
        Intent intent = new Intent(this, ListIngredientPrices.class);
        intent.putExtra("ingredientId", ingredient.getIdIngredient());
        intent.putExtra("origin", "listIngredients");
        startActivity(intent);
    }

    @Override
    public void onResume() {

        list = (ListView) findViewById(R.id.listViewIngredients);
        IngredientModel crud = new IngredientModel(getBaseContext());
        ingredients = crud.listIngredients();
        List<String> ingredientsString = new ArrayList<>();
        List<String> ingredientsUpdate = new ArrayList<>();
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> datum = new HashMap<String, String>();

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

            Double value = ingredient.getLatestValue();
            DecimalFormat numberFormat;
            if(value < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }
            String valor = "R$ " + numberFormat.format(value);

            String ing = ingredient.getName() + " " + ingredient.getBrand() + " - " + valor + " (" +
                    qtd + " " + unity + ")";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", new Locale("pt_BR"));
            String dataFormatada = sdf.format(ingredient.getLastUpdate());
            String lastUpdate = "Última edição em: " + dataFormatada;

            ingredientsString.add(ing);
            ingredientsUpdate.add(ingredient.getLastUpdate().toString());

            datum = new HashMap<String, String>();
            datum.put( "line1", ing);
            datum.put( "line2", lastUpdate );
            data.add( datum );
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"line1", "line2" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openIngredientDetails(view, ingredients.get(position) );
            }
        });

        list.setAdapter(adapter);

        super.onResume();
    }

    public void returnToMain(View v){
        finish();
    }

    public void viewAddIngredient(View v){
        Intent intent = new Intent (this, AddIngredient.class);
        startActivity(intent);
    }
}
