package com.example.bruno.cookcalc.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Controller.RecipePriceController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecipeModel {
    private CreateDataBase banco;

    public RecipeModel(Context context){
        banco = new CreateDataBase(context);
    }

    public Long insertRecipe(RecipeController recipe){
        ContentValues values;
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("portions", recipe.getPortions());
        values.put("minutes", recipe.getMinutes());
        values.put("isBread", recipe.getBread());
        values.put("isFavorite", recipe.getFavorite());
        result = db.insert("recipe", null, values);
        db.close();
        return result;
    }

    public Long insertRecipePrice(RecipePriceController recipePrice){
        ContentValues values;
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("id_recipe", recipePrice.getidRecipe());
        values.put("value", recipePrice.getValue());
        values.put("creation_date", new java.util.Date().toString());
        result = db.insert("recipe", null, values);
        db.close();
        return result;
    }



    public Long updateRecipe(RecipeController recipe){
        ContentValues values;
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("portions", recipe.getPortions());
        values.put("minutes", recipe.getMinutes());
        values.put("isBread", recipe.getBread());
        values.put("isFavorite", recipe.getFavorite());

        String whereClause = "id_recipe = ?";
        String[] whereArgs = new String[] {recipe.getIdRecipe().toString()};
        result = db.update("recipe", values, whereClause, whereArgs);

        db.close();
        return result;
    }

    public List<RecipeController> listRecipes(){
        String[] fields = {
                "id_recipe",
                "name",
                "latest_value",
                "isFavorite"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.query("recipe", fields, null, null, null, null, "isFavorite desc, name asc");

        List<RecipeController> recipes = new ArrayList<>();
        RecipeController recipe = null;
        if (cursor.moveToFirst()) {
            do {
                recipe = new RecipeController();
                recipe.setIdRecipe(cursor.getInt(cursor.getColumnIndex("id_recipe")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setValue(cursor.getDouble(cursor.getColumnIndex("latest_value")));
                recipe.setFavorite( cursor.getInt(cursor.getColumnIndex("isFavorite")) == 1);
                recipes.add(recipe);
            } while (cursor.moveToNext());
        }
        db.close();
        return recipes;
    }

    public List<RecipePriceController> listRecipePrices(Integer idRecipe){
        String[] fields = {
                "id_price",
                "id_recipe",
                "creation_date",
                "value"
        };
        SQLiteDatabase db = banco.getReadableDatabase();

        String whereClause = "id_recipe = ?";
        String[] whereArgs = new String[] {idRecipe.toString()};
        Cursor cursor = db.query("recipe_price", fields, whereClause, whereArgs, null, null, "date(creation_date)");
//        Cursor cursor = db.query("recipe_price", fields, null, null, null, null, "creation_date DESC");

        List<RecipePriceController> recipePrices = new ArrayList<>();
        RecipePriceController recipePrice = null;
        if (cursor.moveToFirst()) {
            do {
                recipePrice = new RecipePriceController();
                recipePrice.setIdPrice(cursor.getInt(cursor.getColumnIndex("id_price")));
                recipePrice.setidRecipe(cursor.getInt(cursor.getColumnIndex("id_recipe")));
                recipePrice.setValue(cursor.getDouble(cursor.getColumnIndex("value")));


                String target  = cursor.getString(cursor.getColumnIndexOrThrow("creation_date"));
                DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                try{
                    Date data =  df.parse(target);
                    recipePrice.setCreationDate(data);
                }catch (ParseException e){
                    e.printStackTrace();
                }

                recipePrices.add(recipePrice);
            } while (cursor.moveToNext());
        }
        db.close();

        List<RecipePriceController> recipePricesRet = new ArrayList<>();
        for(int i = recipePrices.size() -1; i >= 0; i--){
            recipePricesRet.add(recipePrices.get(i));
        }

        return recipePricesRet;
    }

    public void updateRecipeValue(Integer id_recipe) {
        String sql = "SELECT SUM(value) new_value, id_recipe FROM ingredient_recipe " +
                " WHERE id_recipe = " + id_recipe;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        Double newValue = 0.0;
        if (cursor.moveToFirst()) {
            newValue = cursor.getDouble(cursor.getColumnIndex("new_value"));
        }

        ContentValues values= new ContentValues();
        values.put("latest_value", newValue);
        values.put("last_update", new java.util.Date().toString());
        db.update("recipe", values,  " id_recipe = " + id_recipe, null);

        values = new ContentValues();
        values.put("id_recipe", id_recipe);
        values.put("value", newValue);
        values.put("creation_date", new java.util.Date().toString());
        long result = db.insert("recipe_price", null, values);

        db.close();
    }

    public RecipeController getRecipeById(Integer id) {
        String[] fields = {
                "id_recipe",
                "name",
                "isBread",
                "isFavorite",
                "portions",
                "minutes",
                "latest_value"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        RecipeController recipe = null;
        String whereClause = "id_recipe = ?";
        String[] whereArgs = new String[] {id.toString()};
        Cursor cursor = db.query("recipe", fields, whereClause, whereArgs, null, null, null);;

        if (cursor.moveToFirst()) {
            recipe = new RecipeController();
            recipe.setIdRecipe(cursor.getInt(cursor.getColumnIndex("id_recipe")));
            recipe.setMinutes(cursor.getInt(cursor.getColumnIndex("minutes")));
            recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
            recipe.setBread( cursor.getInt(cursor.getColumnIndex("isBread")) == 1);
            recipe.setFavorite( cursor.getInt(cursor.getColumnIndex("isFavorite")) == 1);
            recipe.setPortions(cursor.getDouble(cursor.getColumnIndex("portions")));
            recipe.setValue(cursor.getDouble(cursor.getColumnIndex("latest_value")));

            db.close();
        }
        return recipe;
    }


    public List<RecipeController> getRecipeByIngredient(Integer ingredientId){
        String[] fields = {
                "id_recipe",
                "name",
                "latest_value"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        String[] whereArgs = new String[] {ingredientId.toString(), };
        Cursor cursor = db.rawQuery("SELECT ingredient_recipe.id_ingredient as id_ingredient, " +
                " recipe.id_recipe as id_recipe, " +
                " recipe.name as name, " +
                " recipe.latest_value as latest_value" +
                " FROM ingredient_recipe, recipe  " +
                " WHERE ingredient_recipe.id_ingredient = ?" +
                " AND ingredient_recipe.id_recipe = recipe.id_recipe ", whereArgs);

        List<RecipeController> recipes = new ArrayList<>();
        RecipeController recipe = null;
        if (cursor.moveToFirst()) {
            do {
                recipe = new RecipeController();
                recipe.setIdRecipe(cursor.getInt(cursor.getColumnIndex("id_recipe")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setValue(cursor.getDouble(cursor.getColumnIndex("latest_value")));
                recipes.add(recipe);

            } while (cursor.moveToNext());
        }
        db.close();
        return recipes;
    }

    public Long removeRecipe(Integer idRecipe){
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        String whereClause = "id_recipe = ? ";
        String[] whereArgs = new String[] {idRecipe.toString()};
        result = db.delete("ingredient_recipe", whereClause, whereArgs );
        result = db.delete("recipe", whereClause, whereArgs );

        db.close();
        return result;

    }
}
