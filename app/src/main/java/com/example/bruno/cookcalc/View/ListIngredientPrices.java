package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.IngredientPriceController;
import com.example.bruno.cookcalc.Model.ConfigModel;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.IngredientPriceModel;
import com.example.bruno.cookcalc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ListIngredientPrices extends AppCompatActivity {

    private ListView list;
    private TextView header;
    private Integer ingredientId;
    private IngredientController ingredient;
    private List<IngredientPriceController> ingredientPrices;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    private String state;
    private String city;
    private List <IngredientController> ingredientsAlike;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ingredient_prices);
        Bundle b = getIntent().getExtras();
        ingredientId = b.getInt("ingredientId");
        ingredient = new IngredientModel(getBaseContext()).selectIngredient(ingredientId);
        ingredientPrices =  new IngredientPriceModel(getBaseContext()).listIngredientsPrices(ingredientId);
        header = (TextView) findViewById(R.id.textViewIngredientName);
        header.setText("Seu Histórico de Preços de\n" + ingredient.getName() + " " + ingredient.getBrand() + ":");
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

    public void getFirebaseData(){
        ConfigModel configModel = new ConfigModel(getBaseContext());
        if (configModel.configExists("state")
                && configModel.configExists("city")){
            state = configModel.getConfigValue("state");
            city = configModel.getConfigValue("city");
        } else {
            //Toast.makeText(getBaseContext(), "Não há um estado configurado", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filterData(dataSnapshot.child("users").child(state).child(city.toUpperCase()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void filterData(DataSnapshot dataSnapshot){
        for (DataSnapshot ds : dataSnapshot.getChildren()){
            for (DataSnapshot dsIngredient : ds.child("ingredients").getChildren()){
                if(ingredient.getName().equalsIgnoreCase(dsIngredient.child("name").getValue().toString())
                        && ingredient.getUnity().equalsIgnoreCase(dsIngredient.child("unity").getValue().toString())
                        && ingredient.getQuantity() == Double.parseDouble(dsIngredient.child("quantity").getValue().toString())
                        ){
                    IngredientController ic = new IngredientController();
                    ic.setName(dsIngredient.child("name").getValue().toString());
                    ic.setUnity(dsIngredient.child("unity").getValue().toString());
                    ic.setLatestValue(Double.parseDouble(dsIngredient.child("latestValue").getValue().toString()));
                    ingredientsAlike.add(ic);
                }
            }
        }

        TextView text = (TextView) findViewById(R.id.textViewMean);

        if (ingredientsAlike.size() <=2){
            text.setText("Não há dados para calcular a média de preço do ingrediente na sua cidade\n");

            try{
                text = (TextView) findViewById(R.id.textViewMax);
                ((ViewGroup) text.getParent()).removeView(text);
                text = (TextView) findViewById(R.id.textViewMin);
                ((ViewGroup) text.getParent()).removeView(text);
            } catch (Exception e){
                e.printStackTrace();
            }

        } else {
            Double pricesSum = 0.0;
            Double maxValue = ingredientsAlike.get(0).getLatestValue();
            Double minValue = ingredientsAlike.get(0).getLatestValue();
            for(IngredientController ic : ingredientsAlike){
                pricesSum += ic.getLatestValue();

                if(ic.getLatestValue() > maxValue)
                    maxValue = ic.getLatestValue();

                if(ic.getLatestValue() < minValue)
                    minValue = ic.getLatestValue();
            }

            Double mean = pricesSum/ingredientsAlike.size();
            DecimalFormat numberFormat;
            if(mean < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }
            String texto = "Valor médio na sua cidade: R$ " + numberFormat.format(mean);
            text.setText(texto);


            text = (TextView) findViewById(R.id.textViewMax);
            if(maxValue < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }
            texto = "Valor mais alto encontrado: R$ " + numberFormat.format(maxValue);
            text.setText(texto);

            text = (TextView) findViewById(R.id.textViewMin);
            if(minValue < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }
            texto = "Valor mais baixo encontrado: R$ " + numberFormat.format(minValue);
            text.setText(texto);

        }

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

        ingredientsAlike = new ArrayList<>();
        getFirebaseData();
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
