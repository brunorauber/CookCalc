package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.bruno.cookcalc.Controller.IngredientPriceController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Controller.RecipePriceController;
import com.example.bruno.cookcalc.Model.ConfigModel;
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

public class ListRecipePrices extends Activity {

    private ListView list;
    private TextView header;
    private Integer recipeId;
    private RecipeController recipe;
    private List<RecipePriceController> recipePrices;
    private Double cost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recipe_prices);
        Bundle b = getIntent().getExtras();
        recipeId = b.getInt("recipeId");
        recipe = new RecipeModel(getBaseContext()).getRecipeById(recipeId);
        recipePrices =  new RecipeModel(getBaseContext()).listRecipePrices(recipeId);

        header = (TextView) findViewById(R.id.textViewRecipeName);
        header.setText("Histórico de Custo: " + recipe.getName());
        header.setText("Histórico de Custo: ");
        list = (ListView) findViewById(R.id.listViewIngredientPrices);
        List<String> prices = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> datum = new HashMap<String, String>();
        String dataFormatada = "";
        List<Float> valores = new ArrayList<>();

        for (RecipePriceController price : recipePrices){
            //String qtd = new Boolean(ingredient.getQuantity() % 1 == 0) ?  ingredient.getQuantity().toString().replace(".0", "") : ingredient.getQuantity().toString();
            Double value = price.getValue();
            DecimalFormat numberFormat;
            if(value < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }
            String valor = "R$ " + numberFormat.format(value);
            valores.add(value.floatValue());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", new Locale("pt_BR"));
            if (dataFormatada.equals(sdf.format(price.getCreationDate()))){
                continue;
            }
            dataFormatada = sdf.format(price.getCreationDate());

            prices.add(valor);
            dates.add(dataFormatada);

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
        for(int i = data.size() -1; i >= 0; i--){
            Float valorFormatado = valores.get(i);
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
        DecimalFormat numberFormat;

        TextView text = (TextView) findViewById(R.id.textViewTotalValue);
        if(recipe.getValue() < 1){
            numberFormat = new DecimalFormat("0.00");
        } else {
            numberFormat = new DecimalFormat("#.00");
        }
        text.setText("Custo: " + numberFormat.format(recipe.getValue()));

        text = (TextView) findViewById(R.id.textViewPortions);
        text.setText("Porções/Rendimento: " + recipe.getPortions());

        text = (TextView) findViewById(R.id.textViewValuePerPortion);
        Double valuePerPortion = recipe.getValue()/recipe.getPortions();
        if(valuePerPortion < 1){
            numberFormat = new DecimalFormat("0.00");
        } else {
            numberFormat = new DecimalFormat("#.00");
        }
        text.setText("Custo unitário: " + numberFormat.format(valuePerPortion));

        cost = recipe.getValue() * 1.1;
        ConfigModel configModel = new ConfigModel(getBaseContext());
        Map<String, String> configs = configModel.listConfigs();
        if(configs.containsKey("wageType")
                && configs.containsKey("wageType")){
            String wageType = configs.get("wageType").toString();
            Double hourValue = 0.0;
            if(wageType.equals("month")){
                hourValue = Double.parseDouble(configs.get("hourValue").toString()) / 220;
            } else{
                hourValue = Double.parseDouble(configs.get("hourValue").toString());
            }
            Double time = ((double) recipe.getMinutes() / 60);
            Double timeValue = hourValue * time;
            cost += timeValue;
        }

//        cost = suggested;
        Double suggested = (cost * 1.5);
        text = (TextView) findViewById(R.id.textViewSuggestedValuePerPortion);

//        DecimalFormat numberFormat;
        suggested = suggested/recipe.getPortions();
        if(suggested < 1){
            numberFormat = new DecimalFormat("0.00");
        } else {
            numberFormat = new DecimalFormat("#.00");
        }

        text.setText("Preço de venda sugerido \n(50% de lucro): " + numberFormat.format(suggested));
    }

    public void recalculationDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Quanto de lucro (%) você quer aplicar?");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        builder.setView(input);

        builder.setPositiveButton("Recalcular", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Double qtd = Double.parseDouble(input.getText().toString());
                if(qtd <= 0){
                    showErrorMessage("Valor inválido!");
                    return;
                }
                recalculateProfit(qtd);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(getBaseContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void recalculateProfit (Double percentage){
//        percentage = percentage ;
        TextView text = (TextView) findViewById(R.id.textViewSuggestedValuePerPortion);
        Double value = cost + (cost * percentage / 100);
        DecimalFormat numberFormat;
        DecimalFormat numberFormat2;
        value = value/recipe.getPortions();
        if(value < 1){
            numberFormat = new DecimalFormat("0.00");
        } else {
            numberFormat = new DecimalFormat("#.00");
        }
        if(percentage < 1){
            numberFormat2 = new DecimalFormat("0.00");
        } else {
            if (percentage.intValue() == percentage){
                numberFormat2 = new DecimalFormat("#");
            } else {
                numberFormat2 = new DecimalFormat("#.00");
            }
        }


        text.setText("Preço de venda sugerido \n("+ numberFormat2.format(percentage) +"% de lucro): " + numberFormat.format(value));
    }

    public void showErrorMessage(String msg){
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Aviso");
        //define a mensagem
        builder.setMessage(msg);

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

    public void returnToMain(View v){
        finish();
    }
}
