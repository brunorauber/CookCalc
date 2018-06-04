package com.example.bruno.cookcalc.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.IngredientRecipeController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.CloudModel;
import com.example.bruno.cookcalc.Model.ConfigModel;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.IngredientRecipeModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private CloudModel cloudModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        IngredientModel ingredientModel = new IngredientModel(getBaseContext());
        RecipeModel recipeModel = new RecipeModel(getBaseContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Button btn = (Button) findViewById(R.id.buttonSync);
        btn.setVisibility(View.INVISIBLE);


        ConfigModel configModel = new ConfigModel(getBaseContext());
        if (!configModel.configExists("firstTime")){
            firstConfig();
            configModel.insertConfig("firstTime", "false");
        }

    }

    public void firstConfig(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Antes de usar o CookCalc, gostaríamos de saber algumas informações sobre você.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                firstEmail();
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void lastMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para editar Email, Estado, Cidade e valores de salário" +
                " (para os valores sugeridos de receitas serem mais precisos),\n" +
                "vá à tela de Configurações ");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void firstEmail (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage( "Primeiro, qual seu email?\n" +
                "Prometemos não mandar nenhum spam");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                ConfigModel configModel = new ConfigModel(getBaseContext());
                String email = input.getText().toString();
                if( email.length() >= 5 && email.contains("@") ) {
                    if (configModel.configExists("email")) {
                        configModel.updateConfig("email", email.toLowerCase());
                    } else {
                        configModel.insertConfig("email", email.toLowerCase());
                    }
                    firstState();
                } else{
                    Toast.makeText(getBaseContext(), "Email inválido!", Toast.LENGTH_SHORT).show();
                    firstEmail();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                lastMessage();
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void firstState(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage( "Qual seu estado?");

        final Spinner input = new Spinner(this);
        List<String> statesString = statesList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, statesString);
        input.setAdapter(adapter);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                ConfigModel configModel = new ConfigModel(getBaseContext());
                String state = input.getSelectedItem().toString();
                if( state.length() == 2 ) {
                    if(configModel.configExists("state")){
                        configModel.updateConfig("state", state);
                    } else {
                        configModel.insertConfig("state", state);
                    }
                    firstCity();
                } else{
                    Toast.makeText(getBaseContext(), "Estado inválido!", Toast.LENGTH_SHORT).show();
                    firstState();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                lastMessage();
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void firstCity(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage( "Qual a sua cidade?");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                ConfigModel configModel = new ConfigModel(getBaseContext());
                String city = input.getText().toString();
                if( city.length() > 3) {
                    if(configModel.configExists("city")){
                        configModel.updateConfig("city", city);
                    } else {
                        configModel.insertConfig("city", city);
                    }
                    lastMessage();
                } else{
                    Toast.makeText(getBaseContext(), "Cidade inválida!", Toast.LENGTH_SHORT).show();
                    firstCity();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                lastMessage();
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public List<String> statesList(){
        List<String> statesString = new ArrayList<>();

//        statesString.add("Selecione");
        statesString.add("AC");
        statesString.add("AL");
        statesString.add("AP");
        statesString.add("AM");
        statesString.add("BA");
        statesString.add("CE");
        statesString.add("DF");
        statesString.add("ES");
        statesString.add("GO");
        statesString.add("MA");
        statesString.add("MT");
        statesString.add("MS");
        statesString.add("MG");
        statesString.add("PA");
        statesString.add("PB");
        statesString.add("PR");
        statesString.add("PE");
        statesString.add("PI");
        statesString.add("RJ");
        statesString.add("RN");
        statesString.add("RS");
        statesString.add("RO");
        statesString.add("RR");
        statesString.add("SC");
        statesString.add("SP");
        statesString.add("SE");
        statesString.add("TO");
        return statesString;
    }


    public void viewAddIngredient(View v){
        Intent intent = new Intent (this, AddIngredient.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }

    public void viewListIngredient(View v){
        Intent intent = new Intent (this, ListIngredients.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }
    public void viewAddRecipe(View v){
        Intent intent = new Intent (this, AddRecipe.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }

    public void viewListRecipes(View v){
        Intent intent = new Intent (this, ListRecipes.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }

    public void viewConfig(View v){
        Intent intent = new Intent (this, ConfigView.class);
        intent.putExtra("origin", "main");
        startActivity(intent);
    }

    public void sync(View v){
        ConfigModel configModel = new ConfigModel(getBaseContext());
        String email = null;
        if (configModel.configExists("email")){
            email = configModel.getConfigValue("email");
        }
        if(email == null || email.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há um email configurado", Toast.LENGTH_SHORT).show();
            return;
        }

        String state = null;
        if (configModel.configExists("state")){
            state = configModel.getConfigValue("state");
        }
        if(state == null || state.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há um estado configurado", Toast.LENGTH_SHORT).show();
            return;
        }

        String city = null;
        if (configModel.configExists("city")){
            city = configModel.getConfigValue("city");
        }
        if(city == null || city.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há uma cidade configurada", Toast.LENGTH_SHORT).show();
            return;
        }

        IngredientModel ingredientModel= new IngredientModel(getBaseContext());
        List<IngredientController> ingredients = ingredientModel.listIngredients();

        RecipeModel recipeModel = new RecipeModel(getBaseContext());
        List<RecipeController> recipes = recipeModel.listRecipes();

        IngredientRecipeModel ingredientRecipeModel = new IngredientRecipeModel(getBaseContext());
        for(RecipeController recipe : recipes){
            List<IngredientRecipeController> ingredientRecipes = ingredientRecipeModel.listIngredientsFromRecipe(recipe.getIdRecipe());
            recipe.setIngredients(ingredientRecipes);
        }

        cloudModel = new CloudModel();
        cloudModel.writeNewUser(email, state, city, recipes, ingredients);
        //cloudModel.getSync(state, city);
    }
    /*
    public void sync(){
        ConfigModel configModel = new ConfigModel(getBaseContext());
        String email = null;
        if (configModel.configExists("email")){
            email = configModel.getConfigValue("email");
        }

        if(email == null || email.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há um email configurado", Toast.LENGTH_SHORT).show();
        }
        String state = null;
        if (configModel.configExists("state")){
            state = configModel.getConfigValue("state");
        }

        if(state == null || state.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há um estado configurado", Toast.LENGTH_SHORT).show();
        }
        String city = null;
        if (configModel.configExists("city")){
            city = configModel.getConfigValue("city");
        }

        if(city == null || city.isEmpty()){
            Toast.makeText(getBaseContext(), "Não há uma cidade configurada", Toast.LENGTH_SHORT).show();
        }

        IngredientModel ingredientModel= new IngredientModel(getBaseContext());
        List<IngredientController> ingredients = ingredientModel.listIngredients();

        RecipeModel recipeModel = new RecipeModel(getBaseContext());
        List<RecipeController> recipes = recipeModel.listRecipes();

        IngredientRecipeModel ingredientRecipeModel = new IngredientRecipeModel(getBaseContext());
        for(RecipeController recipe : recipes){
            List<IngredientRecipeController> ingredientRecipes = ingredientRecipeModel.listIngredientsFromRecipe(recipe.getIdRecipe());
            recipe.setIngredients(ingredientRecipes);
        }

        cloudModel = new CloudModel();
        cloudModel.writeNewUser(email, state, city, recipes, ingredients);
//        cloudModel.getSync();
    }
    */
}