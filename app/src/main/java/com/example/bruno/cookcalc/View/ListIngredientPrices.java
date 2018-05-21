package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.IngredientPriceController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.IngredientPriceModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListIngredientPrices extends Activity {

    private ListView list;
    private TextView header;
    private Integer ingredientId;
    private IngredientController ingredient;
    private List<IngredientPriceController> ingredientPrices;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ingredient_prices);
        Bundle b = getIntent().getExtras();
        ingredientId = b.getInt("ingredientId");


        ingredient = new IngredientModel(getBaseContext()).selectIngredient(ingredientId);
        ingredientPrices =  new IngredientPriceModel(getBaseContext()).listIngredientsPrices(ingredientId);

        header = (TextView) findViewById(R.id.textViewIngredientName);
        header.setText("Histórico de Preços: " + ingredient.getName() + " " + ingredient.getBrand());

        list = (ListView) findViewById(R.id.listViewIngredientPrices);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        //Map<String, String> datum = new HashMap<String, String>();

        for (IngredientPriceController price : ingredientPrices){
            String qtd = new Boolean(ingredient.getQuantity() % 1 == 0) ?  ingredient.getQuantity().toString().replace(".0", "") : ingredient.getQuantity().toString();
            String valor = "R$ " + price.getValue();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", new Locale("pt_BR"));
            String dataFormatada = sdf.format(price.getCreationDate());

            Map<String, String> datum = new HashMap<String, String>();
            datum.put( "line1", valor);
            datum.put( "line2", dataFormatada + " :: " + price.getIdPrice() );
            data.add( datum );
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"line1", "line2" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        list.setAdapter(adapter);
    }

    @Override
    public void onResume() {

        ingredientPrices =  new IngredientPriceModel(getBaseContext()).listIngredientsPrices(ingredientId);
        list = (ListView) findViewById(R.id.listViewIngredientPrices);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> datum = new HashMap<String, String>();

        for (IngredientPriceController price : ingredientPrices){
            String qtd = new Boolean(ingredient.getQuantity() % 1 == 0) ?  ingredient.getQuantity().toString().replace(".0", "") : ingredient.getQuantity().toString();

            DecimalFormat numberFormat;
            if(price.getValue() < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }

            String valor = "R$ " + numberFormat.format(price.getValue());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", new Locale("pt_BR"));
            String dataFormatada = sdf.format(price.getCreationDate());

            datum = new HashMap<String, String>();
            datum.put( "line1", valor);
            datum.put( "line2", dataFormatada );
            data.add( datum );
        }

        /*
        LineChart chart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();
        List<Entry> chartEntries = new ArrayList<Entry>();

        //construção da lista para o gráfico
        Float y = 1.0F;
        for(int i = ingredientPrices.size() -1; i >= 0; i--){
            IngredientPriceController price = ingredientPrices.get(i);
            DecimalFormat numberFormat;
            if(price.getValue() < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }
            Float valorFormatado = Float.parseFloat(numberFormat.format(price.getValue()));
            entries.add(new Entry(y++, valorFormatado));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.BLACK);
        dataSet.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
        */

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"line1", "line2" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        list.setAdapter(adapter);
        super.onResume();
    }


    public void editIngredient(View v){
        Intent intent = new Intent(this, AddIngredient.class);
        intent.putExtra("ingredientId", ingredientId);
        startActivity(intent);
    }


    public void returnToMain(View v){
        finish();
    }
}
