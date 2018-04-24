package com.example.bruno.cookcalc.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bruno.cookcalc.Controller.IngredientPriceController;
import com.example.bruno.cookcalc.Controller.IngredientRecipeController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IngredientPriceModel {
    private CreateDataBase banco;

    public IngredientPriceModel(Context context){
        banco = new CreateDataBase(context);
    }

/*    public Long insertIngredientRecipe(IngredientRecipeController ingredientRecipe){
        ContentValues values;
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("id_ingredient", ingredientRecipe.getIdIngredient());
        values.put("id_recipe", ingredientRecipe.getIdRecipe());
        values.put("value", ingredientRecipe.getValue());
        values.put("quantity", ingredientRecipe.getQuantity());
        result = db.insert("ingredient_recipe", null, values);
        db.close();
        return result;
    }*/

    public List<IngredientPriceController> listIngredientsPrices(Integer idIngredient){

        SQLiteDatabase db = banco.getReadableDatabase();
        String[] whereArgs = new String[] {idIngredient.toString()};
        Cursor cursor = db.rawQuery("SELECT *" +
                " FROM ingredient_price  " +
                " WHERE id_ingredient = ? "+
                " ORDER BY creation_date ASC", whereArgs);


        List<IngredientPriceController> ingredientPrices = new ArrayList<>();
        IngredientPriceController ingredientPrice = null;
        if (cursor.moveToFirst()) {
            do {
                ingredientPrice = new IngredientPriceController();
                ingredientPrice.setIdPrice(cursor.getInt(cursor.getColumnIndex("id_price")));
                ingredientPrice.setIdIngredient(cursor.getInt(cursor.getColumnIndex("id_ingredient")));
                ingredientPrice.setValue(cursor.getDouble(cursor.getColumnIndex("value")));

                String target  = cursor.getString(cursor.getColumnIndexOrThrow("creation_date"));
                DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                try{
                    Date data =  df.parse(target);
                    ingredientPrice.setCreationDate(data);
                }catch (ParseException e){
                    e.printStackTrace();
                }

                ingredientPrices.add(ingredientPrice);
            } while (cursor.moveToNext());
        }

        db.close();
        return ingredientPrices;
    }
}
