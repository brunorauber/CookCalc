package com.example.bruno.cookcalc.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.IngredientPriceController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        values.put("brand", ingredient.getBrand());
        values.put("latest_value", ingredient.getLatestValue());
        values.put("unity", ingredient.getUnity());
        values.put("quantity", ingredient.getQuantity());
        values.put("last_update", new java.util.Date().toString());
        result = db.insert("ingredient", null, values);

        values = new ContentValues();
        values.put("id_ingredient", result);
        values.put("value", ingredient.getLatestValue());
        values.put("creation_date", new java.util.Date().toString());
        result = db.insert("ingredient_price", null, values);

        db.close();
        return result;
    }

    public Long updateIngredient(IngredientController ingredient){
        ContentValues values;
        long result;
        IngredientController oldIngredient = selectIngredient(ingredient.getIdIngredient());

        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("name", ingredient.getName());
        values.put("brand", ingredient.getBrand());
        values.put("latest_value", ingredient.getLatestValue());
        values.put("unity", ingredient.getUnity());
        values.put("quantity", ingredient.getQuantity());
        values.put("last_update", new java.util.Date().toString());

        String whereClause = "id_ingredient = ?";
        String[] whereArgs = new String[] {ingredient.getIdIngredient().toString()};
        result = db.update("ingredient", values, whereClause, whereArgs);

        if (!oldIngredient.getLatestValue().equals(ingredient.getLatestValue())){
            values = new ContentValues();
            values.put("id_ingredient", ingredient.getIdIngredient().toString());
            values.put("value", ingredient.getLatestValue());
            values.put("creation_date", new java.util.Date().toString());
            result = db.insert("ingredient_price", null, values);
        }

        db.close();
        return result;
    }


    public IngredientController selectIngredient(Integer idIngredient){
        String[] fields = {
                "id_ingredient",
                "name",
                "brand",
                "latest_value",
                "unity",
                "quantity",
                "last_update"
        };

        SQLiteDatabase db = banco.getReadableDatabase();
        /*
        Cursor cursor = db.rawQuery("SELECT *" + " FROM ingredient  " +
                " WHERE id_ingredient = " + idIngredient, null);
        */
        String whereClause = "id_ingredient = ?";
        String[] whereArgs = new String[] {idIngredient.toString()};
        Cursor cursor = db.query("ingredient", fields, whereClause, whereArgs, null, null, null);;


        List<IngredientPriceController> ingredientPrices = new ArrayList<>();
        IngredientController ingredient = null;
        if (cursor.moveToFirst()) {
            do {
                ingredient = new IngredientController();
                ingredient.setIdIngredient(cursor.getInt(cursor.getColumnIndex("id_ingredient")));
                ingredient.setName(cursor.getString(cursor.getColumnIndex("name")));
                ingredient.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
                ingredient.setLatestValue(cursor.getDouble(cursor.getColumnIndex("latest_value")));
                ingredient.setUnity(cursor.getString(cursor.getColumnIndex("unity")));
                ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndex("quantity")));

                String target  = cursor.getString(cursor.getColumnIndexOrThrow("last_update"));
                DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                try{
                    Date data =  df.parse(target);
                    ingredient.setLastUpdate(data);
                }catch (ParseException e){
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        return ingredient;

    }

    public List<IngredientController> listIngredients(){
        String[] fields = {
            "id_ingredient",
            "name",
            "brand",
            "latest_value",
            "unity",
            "quantity",
            "last_update"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        List<IngredientController> ingredients = new ArrayList<>();
        IngredientController ingredient = null;
        Cursor cursor = db.query("ingredient", fields,
                null, null, null, null, "name asc, brand asc");
        if (cursor.moveToFirst()) {
            do {
                ingredient = new IngredientController();
                ingredient.setIdIngredient(cursor.getInt(cursor.getColumnIndex("id_ingredient")));
                ingredient.setName(cursor.getString(cursor.getColumnIndex("name")));
                ingredient.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
                ingredient.setLatestValue(cursor.getDouble(cursor.getColumnIndex("latest_value")));
                ingredient.setUnity(cursor.getString(cursor.getColumnIndex("unity")));
                ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndex("quantity")));

                String target  = cursor.getString(cursor.getColumnIndexOrThrow("last_update"));
                DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                try{
                    Date data =  df.parse(target);
                    ingredient.setLastUpdate(data);
                }catch (ParseException e){
                    e.printStackTrace();
                }

                ingredients.add(ingredient);
            } while (cursor.moveToNext());
        }


        String[] fields2 = {
                "id_price"
                ,"id_ingredient"
                ,"creation_date"
                ,"(strftime('%s', creation_date) * 1000) AS added_on"
                ,"value"
        };
        Cursor cursor2 = db.rawQuery(
                "SELECT id_price, id_ingredient, creation_date, (strftime('%s', creation_date) * 1000) AS added_on, VALUE " +
                        "FROM ingredient_price",
                new String[0]
        );
        //Cursor cursor2 = db.query("ingredient_price", fields2, null, null, null, null, null);

        List<IngredientPriceController> ipList = new ArrayList<>();
        IngredientPriceController ip = null;
        if (cursor2.moveToFirst()) {
            do {
                ip = new IngredientPriceController();
                ip.setIdIngredient(cursor2.getInt(cursor2.getColumnIndex("id_ingredient")));
                ip.setIdPrice(cursor2.getInt(cursor2.getColumnIndex("id_price")));
                ip.setValue(cursor2.getDouble(cursor2.getColumnIndex("value")));
                //ip.setCreationDate(new Date(cursor2.getLong(cursor2.getColumnIndexOrThrow("added_on"))));
                String target  = cursor2.getString(cursor2.getColumnIndexOrThrow("creation_date"));
                DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                try{
                    Date data =  df.parse(target);
                    ip.setCreationDate(data);
                }catch (ParseException e){
                    e.printStackTrace();
                }
            } while (cursor2.moveToNext());
        }


        db.close();
        return ingredients;
    }
}
