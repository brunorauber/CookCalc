package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bruno.cookcalc.Controller.IngredientRecipeController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.IngredientRecipeModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListIngredientRecipes extends Activity {

    private ListView list;
    private Integer recipeId;
    List<IngredientRecipeController> ingredientRecipes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_ingredients_recipe);
        Bundle b = getIntent().getExtras();
        recipeId = b.getInt("recipeId");
        addTitle();

        list = (ListView) findViewById(R.id.listViewIngredientsRecipe);
        IngredientRecipeModel crud = new IngredientRecipeModel(getBaseContext());
        ingredientRecipes = crud.listIngredientsFromRecipe(recipeId);
        List<String> ingredientRecipesString = new ArrayList<>();

        for (IngredientRecipeController item : ingredientRecipes){
            String unity = item.getUnity();
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

            Double value = item.getValue();
            DecimalFormat numberFormat;
            if(value < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }

            String valor = "R$ " + numberFormat.format(value);
            String rec = item.getIngredientName()
                    + " " + item.getIngredientBrand()
                    + " - " + valor
                    + " (" +
                    item.getQuantity() + " " + unity + ")";
            ingredientRecipesString.add(rec);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientRecipesString);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openIngredientDetails(view, ingredientRecipes.get(position) );
            }
        });

        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                            ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Menu.NONE, 1, Menu.NONE, "Remover");
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        Integer position = menuInfo.position;
        IngredientRecipeController irController = ingredientRecipes.get(position);
        IngredientRecipeModel irModel =  new IngredientRecipeModel(getBaseContext());

        switch (item.getItemId()) {
            case 1:
                irModel.removeIngredientFromRecipe(irController.getIdRecipe(), irController.getIdIngredient());
                RecipeModel recipeModel = new RecipeModel(getBaseContext());
                recipeModel.updateRecipeValue(recipeId);
                onResume();
                Toast.makeText(getBaseContext(), "Item Removido", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }


    public void openRecipeHistory(View v){
        Intent intent = new Intent(this, ListRecipePrices.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }
    public void openIngredientDetails(View v, IngredientRecipeController ingredient){
        Intent intent = new Intent(this, AddIngredientRecipes.class);
        intent.putExtra("ingredientId", ingredient.getIdIngredient());
        intent.putExtra("recipeId", ingredient.getIdRecipe());
        startActivity(intent);
    }

    public void returnToMain(View v){
        finish();
    }

    public void openIngredientsList(View v){
        Intent intent = new Intent(this, AddIngredientRecipes.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }
    public void editRecipe(View v){
        Intent intent = new Intent(this, AddRecipe.class);
        intent.putExtra("recipeId", recipeId);
        startActivity(intent);
    }

    public void multiplicationDialog(View v){
        if (ingredientRecipes.size() < 1){
            showErrorMessage("A receita não tem nenhum ingrediente!");
        } else {
            showMultiplyMessage();
        }
    }

    public void showMultiplyMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Por quanto você quer multiplicar está receita?");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Double qtd = Double.parseDouble(input.getText().toString());
                if(qtd <= 1){
                    showErrorMessage("Valor inválido!");
                    return;
                }
                multiplyRecipe(qtd);
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

    public void multiplyRecipe(Double qtd){
        RecipeModel model = new RecipeModel(getBaseContext());
        RecipeController recipeOriginal = model.getRecipeById(recipeId);

        IngredientRecipeModel irModel =  new IngredientRecipeModel(getBaseContext());
        //List<IngredientRecipeController> originalList = irModel.listIngredientsFromRecipe(recipeId);

        if(!recipeOriginal.getBread()){
            RecipeController recipeMultiplied = new RecipeController();
            recipeMultiplied.setName(recipeOriginal.getName() + " (x " + qtd + ")");
            recipeMultiplied.setPortions(recipeOriginal.getPortions() * qtd);
            Long idMultiplied = model.insertRecipe(recipeMultiplied);
            for(IngredientRecipeController ir : ingredientRecipes){
                ir.setIdRecipe(idMultiplied.intValue());
                ir.setQuantity(ir.getQuantity()*qtd);
                ir.setValue(ir.getValue()*qtd);
                irModel.insertIngredientRecipe(ir);
            }
            model.updateRecipeValue(idMultiplied.intValue());
            Toast.makeText(getBaseContext(), "Multiplicação gerada com sucesso", Toast.LENGTH_SHORT).show();
        } else{
            List<IngredientRecipeController> baseList = new ArrayList<>();
            for (IngredientRecipeController ir : ingredientRecipes){
                if (ir.getBreadBase()){
                    baseList.add(ir);
                }
            }

            if (baseList.size() < 1){
                showErrorMessage("Esta receita está marcada como um pão, mas nenhum ingrediente está marcado como base!");
//                Toast.makeText(getBaseContext(), "Esta receita está marcada como um pão, mas nenhum ingrediente está marcado como base!", Toast.LENGTH_SHORT).show();
            } else {
                showErrorMessage("Entrou aqui");
            }
        }

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

    @Override
    public void onResume(){
        list = (ListView) findViewById(R.id.listViewIngredientsRecipe);
        IngredientRecipeModel crud = new IngredientRecipeModel(getBaseContext());
        ingredientRecipes = crud.listIngredientsFromRecipe(recipeId);
        List<String> ingredientRecipesString = new ArrayList<>();

        for (IngredientRecipeController item : ingredientRecipes){
            String unity = item.getUnity();
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

            Double value = item.getValue();
            DecimalFormat numberFormat;
            if(value < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }

            String valor = "R$ " + numberFormat.format(value);

            String rec = item.getIngredientName()
                    + " " + item.getIngredientBrand()
                    + " - " + valor
                    + " (" +
                    item.getQuantity() + " " + unity + ")";
            ingredientRecipesString.add(rec);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ingredientRecipesString);
        list.setAdapter(adapter);
        addTitle();
        super.onResume();
    }

    public void addTitle(){
        RecipeModel recipeModel = new RecipeModel(getBaseContext());
        RecipeController recipe = recipeModel.getRecipeById(recipeId);
        Double value = recipe.getValue();
        DecimalFormat numberFormat;
        if(value < 1){
            numberFormat = new DecimalFormat("0.00");
        } else {
            numberFormat = new DecimalFormat("#.00");
        }
        String valor = "R$ " + numberFormat.format(value);
        String title = recipe.getName() + " - " + valor;
        TextView header = (TextView) findViewById(R.id.textViewHeader);
        header.setText(title);
    }

}
