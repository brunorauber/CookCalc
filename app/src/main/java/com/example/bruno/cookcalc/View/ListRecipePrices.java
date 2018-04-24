package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bruno.cookcalc.Controller.IngredientPriceController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Controller.RecipePriceController;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.IngredientPriceModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListRecipePrices extends Activity {

    private ListView list;
    private TextView header;
    private Integer recipeId;
    private RecipeController recipe;
    private List<RecipePriceController> recipePrices;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recipe_prices);
        Bundle b = getIntent().getExtras();
        recipeId = b.getInt("recipeId");
        recipe = new RecipeModel(getBaseContext()).getRecipeById(recipeId);
        recipePrices =  new RecipeModel(getBaseContext()).listRecipePrices(recipeId);

        header = (TextView) findViewById(R.id.textViewIngredientName);
        header.setText("Histórico de Preços: " + recipe.getName());
        list = (ListView) findViewById(R.id.listViewIngredientPrices);
        List<String> prices = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> datum = new HashMap<String, String>();

        for (RecipePriceController price : recipePrices){
            //String qtd = new Boolean(ingredient.getQuantity() % 1 == 0) ?  ingredient.getQuantity().toString().replace(".0", "") : ingredient.getQuantity().toString();
            String valor = "R$ " + price.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", new Locale("pt_BR"));
            String dataFormatada = sdf.format(price.getCreationDate());

            prices.add(valor);
            dates.add(dataFormatada);

            datum = new HashMap<String, String>();
            datum.put( "line1", valor);
            datum.put( "line2", dataFormatada );
            data.add( datum );
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"line1", "line2" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        list.setAdapter(adapter);
    }

    public void returnToMain(View v){
        finish();
    }
}
