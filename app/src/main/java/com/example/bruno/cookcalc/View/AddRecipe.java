package com.example.bruno.cookcalc.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.IngredientRecipeController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Model.CloudModel;
import com.example.bruno.cookcalc.Model.ConfigModel;
import com.example.bruno.cookcalc.Model.IngredientModel;
import com.example.bruno.cookcalc.Model.IngredientRecipeModel;
import com.example.bruno.cookcalc.Model.RecipeModel;
import com.example.bruno.cookcalc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddRecipe extends AppCompatActivity {

    private Integer recipeId;
    private String origin;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    private List <RecipeController> searchResult;
    private String recipeNameSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe);

        Bundle b = getIntent().getExtras();
        if (b != null && b.containsKey("recipeId") ) {
            Button btSearch = (Button) findViewById(R.id.buttonSearch);
            btSearch.setVisibility(View.INVISIBLE);
            ((ViewGroup) btSearch.getParent()).removeView(btSearch);

            recipeId = b.getInt("recipeId");
            fillEditTextFields(recipeId);
        }
        if (b != null && b.containsKey("origin") ) {
            origin = b.getString("origin");

            // Enquanto busca de receitas não funciona
            Button btSearch = (Button) findViewById(R.id.buttonSearch);
            btSearch.setVisibility(View.INVISIBLE);
            ((ViewGroup) btSearch.getParent()).removeView(btSearch);
        } else{
            origin = "";
        }

        CheckBox cb = (CheckBox) findViewById(R.id.checkBoxBread);
        cb.setVisibility(View.INVISIBLE);
//        ((ViewGroup) cb.getParent()).removeView(cb);
    }

    public void saveRecipe(View v){
        if(recipeId == null){
            insertRecipe(v);
        } else{
            updateRecipe(v);
        }
        try {
            sync();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sync(){
        ConfigModel configModel = new ConfigModel(getBaseContext());
        String email = null;
        if (configModel.configExists("email")){
            email = configModel.getConfigValue("email");
        }

        if(email == null || email.isEmpty()){
            return;
        }
        String state = null;
        if (configModel.configExists("state")){
            state = configModel.getConfigValue("state");
        }

        if(state == null || state.isEmpty()){
            return;
        }
        String city = null;
        if (configModel.configExists("city")){
            city = configModel.getConfigValue("city");
        }

        if(city == null || city.isEmpty()){
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

        CloudModel cloudModel = new CloudModel();
        cloudModel.writeNewUser(email, state, city, recipes, ingredients);
    }

    public void updateRecipe(View v){
        RecipeController recipeOld = new RecipeModel(getBaseContext()).getRecipeById(recipeId);
        RecipeController recipeNew = new RecipeController();
        recipeNew.setIdRecipe(recipeId);

        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipeNew.setName(text.getText().toString().trim());

        text = (EditText) findViewById(R.id.editTextPortions);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipeNew.setPortions(Double.parseDouble(text.getText().toString()));

        text = (EditText) findViewById(R.id.editTextTime);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipeNew.setMinutes(Integer.parseInt(text.getText().toString()));

        CheckBox isBread = (CheckBox) findViewById(R.id.checkBoxBread);
        recipeNew.setBread(isBread.isChecked());
        CheckBox isFavorite = (CheckBox) findViewById(R.id.checkBoxFavorite);
        recipeNew.setFavorite(isFavorite.isChecked());

        RecipeModel model = new RecipeModel(getBaseContext());

        if (!recipeOld.getName().equals(recipeNew.getName())
                || !recipeOld.getPortions().equals(recipeNew.getPortions())
                || !recipeOld.getBread().equals(recipeNew.getBread())
                || !recipeOld.getFavorite().equals(recipeNew.getFavorite())
                ){
            model.updateRecipe(recipeNew);
        }
        finish();
    }

    public void insertRecipe(View v){
        RecipeController recipe = new RecipeController();

        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipe.setName(text.getText().toString().trim());

        text = (EditText) findViewById(R.id.editTextPortions);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipe.setPortions(Double.parseDouble(text.getText().toString()));

        text = (EditText) findViewById(R.id.editTextTime);
        if( text.getText().toString().length()<1 ){
            showErrorMessage();
            return;
        }
        recipe.setMinutes(Integer.parseInt(text.getText().toString()));

        CheckBox isBread = (CheckBox) findViewById(R.id.checkBoxBread);
        recipe.setBread(isBread.isChecked());

        CheckBox isFavorite = (CheckBox) findViewById(R.id.checkBoxFavorite);
        recipe.setFavorite(isFavorite.isChecked());

        RecipeModel model = new RecipeModel(getBaseContext());
        model.insertRecipe(recipe);
        finish();
        if(origin.equalsIgnoreCase("main")) {
            Intent intent = new Intent(this, ListRecipes.class);
            startActivity(intent);
        }
    }

    public void showErrorMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Preencha todos os campos!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(getBaseContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });

        /*builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getBaseContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });*/
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void showErrorMessage(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage(msg);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(getBaseContext(), "positivo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });

        /*builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getBaseContext(), "negativo=" + arg1, Toast.LENGTH_SHORT).show();
            }
        });*/
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    public void fillEditTextFields(Integer recipeId){
        RecipeController recipe = new RecipeModel(getBaseContext()).getRecipeById(recipeId);
        EditText text;
        text = (EditText) findViewById(R.id.editTextName);
        text.setText(recipe.getName());

        text = (EditText) findViewById(R.id.editTextPortions);
        text.setText(recipe.getPortions().toString());

        text = (EditText) findViewById(R.id.editTextTime);
        text.setText(recipe.getMinutes().toString());

        CheckBox isBread = (CheckBox) findViewById(R.id.checkBoxBread);
        isBread.setChecked(recipe.getBread());
        CheckBox isFavorite= (CheckBox) findViewById(R.id.checkBoxFavorite);
        isFavorite.setChecked(recipe.getFavorite());
    }

    public void listSearchResult(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Resultado da busca:");
        ListView list = (ListView) findViewById(R.id.listViewIngredientPrices);

        List<String> recipesString = new ArrayList<>();
        for (RecipeController recipe : searchResult){
            Double value = recipe.getValue();
            DecimalFormat numberFormat;
            if(value < 1){
                numberFormat = new DecimalFormat("0.00");
            } else {
                numberFormat = new DecimalFormat("#.00");
            }
            String valor = "R$ " + numberFormat.format(value);
            String rec = recipe.getName() + " - " + valor;
            recipesString.add(rec);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipesString);

        try {
            list.setAdapter(adapter);
            builder.setView(list);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchRecipe(View v){
        searchResult = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Qual receita você está procurando?");

        final EditText input = new EditText(this);
        EditText text = (EditText) findViewById(R.id.editTextName);
        input.setText(text.getText());
        //input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                recipeNameSearch = input.getText().toString();
                searchRecipe();
                //listSearchResult();
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

    public void searchRecipe (){
         mDatabase = FirebaseDatabase.getInstance().getReference();
        //Query query = mDatabase.child("users").orderByChild("id").equalTo;
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filterData(dataSnapshot.child("users"));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void fillSearchResult(DataSnapshot dataSnapshot){
        /*System.out.println("QWERTYUIO");
        System.out.println("QWERTYUIO");
        System.out.println("QWERTYUIO");
        System.out.println(dataSnapshot);*/

        RecipeController rc =  new RecipeController();
        try{
            rc.setName(dataSnapshot.child("name").getValue().toString());
            rc.setPortions(Double.parseDouble(dataSnapshot.child("portions").getValue().toString()));
            rc.setMinutes(Integer.parseInt(dataSnapshot.child("minutes").getValue().toString()));
            rc.setIngredients(new ArrayList<IngredientRecipeController>());
            for(DataSnapshot dataIngredient : dataSnapshot.child("ingredients").getChildren()){
                System.out.println("GYBJEERTHVW3RHR");
                System.out.println(dataIngredient);
            }
            searchResult.add(rc);
        }catch (Exception e){
            e.printStackTrace();
        }

        /*System.out.println("POIUYTRFDE");
        System.out.println("POIUYTRFDE");
        System.out.println("POIUYTRFDE");
        System.out.println(rc);*/
    }

    public void filterData(DataSnapshot dataSnapshot){
        for (DataSnapshot dataState : dataSnapshot.getChildren()){
            for (DataSnapshot dataCity : dataState.getChildren()){
                for (DataSnapshot dataEmail : dataCity.getChildren()){
                    for (DataSnapshot dataUserRecipes : dataEmail.getChildren()){
                        for (DataSnapshot dataRecipe : dataUserRecipes.getChildren()){
                            try{
                                String recipeData = dataRecipe.child("name").getValue().toString();
                                if( recipeData != null &&
                                        (recipeNameSearch.equalsIgnoreCase(recipeData)
                                        || recipeNameSearch.contains(recipeData)
                                        || recipeData.contains(recipeNameSearch))){
                                    fillSearchResult(dataRecipe);
                                }
                            } catch (Exception e){
                                System.out.println("Exception");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }



    public void returnToMain(View v){
        finish();
    }
}
