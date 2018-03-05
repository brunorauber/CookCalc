package com.example.bruno.cookcalc.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bruno.cookcalc.Controller.IngredientController;

import java.util.ArrayList;
import java.util.List;

public class IngredientModel {
    private CreateDataBase banco;

    public IngredientModel(Context context){
        banco = new CreateDataBase(context);
    }

    public Long insertIngredient(IngredientController ingredient){
        ContentValues values;
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("name", ingredient.getName());
        values.put("value", ingredient.getValue());
        values.put("unity", ingredient.getUnity());
        values.put("quantity", ingredient.getQuantity());
        result = db.insert("ingredient", null, values);
        db.close();
        return result;
    }

    public List<IngredientController> listIngredients(){
        String[] fields = {
            "id_ingredient",
            "name",
            "value",
            "unity",
            "quantity"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.query("ingredient", fields, null, null, null, null, null);

        List<IngredientController> ingredients = new ArrayList<>();
        IngredientController ingredient = null;
        if (cursor.moveToFirst()) {
            do {
                ingredient = new IngredientController();
                ingredient.setIdIngredient(cursor.getInt(cursor.getColumnIndex("id_ingredient")));
                ingredient.setName(cursor.getString(cursor.getColumnIndex("name")));
                ingredient.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
                ingredient.setUnity(cursor.getString(cursor.getColumnIndex("unity")));
                ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndex("quantity")));
                ingredients.add(ingredient);
            } while (cursor.moveToNext());
        }

        db.close();
        return ingredients;
    }
}
