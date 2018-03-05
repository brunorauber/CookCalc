package com.example.bruno.cookcalc.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bruno.cookcalc.Controller.RecipeController;

import java.util.ArrayList;
import java.util.List;

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
        result = db.insert("recipe", null, values);
        db.close();
        return result;
    }

    public List<RecipeController> listRecipes(){
        String[] fields = {
            "id_recipe",
            "name",
            "value"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.query("recipe", fields, null, null, null, null, null);

        List<RecipeController> recipes = new ArrayList<>();
        RecipeController recipe = null;
        if (cursor.moveToFirst()) {
            do {
                recipe = new RecipeController();
                recipe.setIdRecipe(cursor.getInt(cursor.getColumnIndex("id_recipe")));
                recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
                recipe.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
                recipes.add(recipe);
            } while (cursor.moveToNext());
        }
        db.close();
        return recipes;
    }

    public void updateRecipeValue(int id) {
        String sql = "SELECT SUM(value) new_value, id_recipe FROM ingredient_recipe " +
                " WHERE id_recipe = " + id;
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        Double newValue = 0.0;
        if (cursor.moveToFirst()) {
            newValue = cursor.getDouble(cursor.getColumnIndex("new_value"));
        }

        ContentValues values= new ContentValues();
        values.put("value", newValue);
        db.update("recipe", values,  " id_recipe = " + id, null);
        db.close();
//        sql = "UPDATE recipe" +
//            "SET value = " + newValue +
//            " WHERE id_recipe = " + id;
//
//        cursor = db.rawQuery(sql, null);
//        cursor.moveToFirst();
//        cursor.close();




    }

    public RecipeController getRecipeById(int id) {
        String[] fields = {
                "id_recipe",
                "name",
                "value"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        String sql = "SELECT *   FROM recipe  WHERE id_recipe = " + id;
        RecipeController recipe = null;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            recipe = new RecipeController();
            recipe.setIdRecipe(cursor.getInt(cursor.getColumnIndex("id_recipe")));
            recipe.setName(cursor.getString(cursor.getColumnIndex("name")));
            recipe.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
            db.close();
        }
        return recipe;
    }
}
