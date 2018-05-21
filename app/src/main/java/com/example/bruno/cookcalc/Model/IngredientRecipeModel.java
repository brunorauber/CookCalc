package com.example.bruno.cookcalc.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bruno.cookcalc.Controller.IngredientRecipeController;

import java.util.ArrayList;
import java.util.List;

public class IngredientRecipeModel {
    private CreateDataBase banco;

    public IngredientRecipeModel(Context context){
        banco = new CreateDataBase(context);
    }

    public Long insertIngredientRecipe(IngredientRecipeController ingredientRecipe){
        ContentValues values;
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("id_ingredient", ingredientRecipe.getIdIngredient());
        values.put("id_recipe", ingredientRecipe.getIdRecipe());
        values.put("value", ingredientRecipe.getValue());
        values.put("quantity", ingredientRecipe.getQuantity());
        values.put("breadBase", ingredientRecipe.getBreadBase());
        result = db.insert("ingredient_recipe", null, values);
        db.close();
        return result;
    }

    public Long updateIngredientRecipe(IngredientRecipeController ingredientRecipe){
        ContentValues values;
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("value", ingredientRecipe.getValue());
        values.put("quantity", ingredientRecipe.getQuantity());
        values.put("breadBase", ingredientRecipe.getBreadBase());
        String whereClause = "id_recipe = ? AND id_ingredient = ?";
        String[] whereArgs = new String[] {ingredientRecipe.getIdRecipe().toString(),
                ingredientRecipe.getIdIngredient().toString()};
        result = db.update("ingredient_recipe", values, whereClause, whereArgs );
        db.close();
        return result;
    }
    public Long updateIngredientRecipeValue(Integer ingredientId, Integer recipeId,  Double newValue){
        ContentValues values;
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("value", newValue);
        String whereClause = "id_ingredient = ? AND id_recipe = ?";
        String[] whereArgs = new String[] {ingredientId.toString(), recipeId.toString()};
        result = db.update("ingredient_recipe", values, whereClause, whereArgs );
        db.close();
        return result;
    }



    public List<IngredientRecipeController> listIngredientsFromRecipe(Integer id_recipe){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ingredient_recipe.id_ingredient as id_ingredient, " +
                " ingredient_recipe.id_recipe as id_recipe, " +
                " ingredient_recipe.value as value, " +
                " ingredient_recipe.quantity as quantity, " +
                " ingredient_recipe.breadBase as breadBase, " +
                " ingredient.name as name, " +
                " ingredient.brand as brand, " +
                " ingredient.unity as unity" +
                " FROM ingredient_recipe, ingredient  " +
                " WHERE id_recipe = " + id_recipe +
                " AND ingredient_recipe.id_ingredient = ingredient.id_ingredient", null);


        List<IngredientRecipeController> ingredientsRecipe = new ArrayList<>();
        IngredientRecipeController ingredientRecipe = null;

        if (cursor.moveToFirst()) {
            do {
                ingredientRecipe = new IngredientRecipeController();
                ingredientRecipe.setIdIngredient(cursor.getInt(cursor.getColumnIndex("id_ingredient")));
                ingredientRecipe.setIdRecipe(cursor.getInt(cursor.getColumnIndex("id_recipe")));
                ingredientRecipe.setIngredientName(cursor.getString(cursor.getColumnIndex("name")));
                ingredientRecipe.setIngredientBrand(cursor.getString(cursor.getColumnIndex("brand")));
                ingredientRecipe.setUnity(cursor.getString(cursor.getColumnIndex("unity")));
                ingredientRecipe.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
                ingredientRecipe.setQuantity(cursor.getDouble(cursor.getColumnIndex("quantity")));
                ingredientRecipe.setBreadBase( cursor.getInt(cursor.getColumnIndex("breadBase")) == 1);

                ingredientsRecipe.add(ingredientRecipe);
            } while (cursor.moveToNext());
        }

        db.close();
        return ingredientsRecipe;
    }


    public List<IngredientRecipeController> getIngredientsById(Integer id_ingredient){
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ingredient_recipe.id_ingredient as id_ingredient, " +
                " ingredient_recipe.id_recipe as id_recipe, " +
                " ingredient_recipe.value as value, " +
                " ingredient_recipe.quantity as quantity, " +
                " ingredient_recipe.breadBase as breadBase, " +
                " ingredient.name as name, " +
                " ingredient.brand as brand, " +
                " ingredient.unity as unity" +
                " FROM ingredient_recipe, ingredient  " +
                " WHERE ingredient.id_ingredient = " + id_ingredient +
                " AND ingredient_recipe.id_ingredient = ingredient.id_ingredient", null);


        List<IngredientRecipeController> ingredientsRecipe = new ArrayList<>();
        IngredientRecipeController ingredientRecipe = null;
        if (cursor.moveToFirst()) {
            do {
                ingredientRecipe = new IngredientRecipeController();
                ingredientRecipe.setIdIngredient(cursor.getInt(cursor.getColumnIndex("id_ingredient")));
                ingredientRecipe.setIdRecipe(cursor.getInt(cursor.getColumnIndex("id_recipe")));
                ingredientRecipe.setIngredientName(cursor.getString(cursor.getColumnIndex("name")));
                ingredientRecipe.setIngredientBrand(cursor.getString(cursor.getColumnIndex("brand")));
                ingredientRecipe.setUnity(cursor.getString(cursor.getColumnIndex("unity")));
                ingredientRecipe.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
                ingredientRecipe.setQuantity(cursor.getDouble(cursor.getColumnIndex("quantity")));
                ingredientRecipe.setBreadBase( cursor.getInt(cursor.getColumnIndex("breadBase")) == 1);
                ingredientsRecipe.add(ingredientRecipe);
            } while (cursor.moveToNext());
        }

        db.close();
        return ingredientsRecipe;
    }


    public IngredientRecipeController getIngredientRecipe(Integer id_recipe, Integer id_ingredient){
        SQLiteDatabase db = banco.getReadableDatabase();
        String[] whereArgs = new String[] {id_recipe.toString(), id_ingredient.toString(), };
        Cursor cursor = db.rawQuery("SELECT ingredient_recipe.id_ingredient as id_ingredient, " +
                " ingredient_recipe.id_recipe as id_recipe, " +
                " ingredient_recipe.value as value, " +
                " ingredient_recipe.quantity as quantity, " +
                " ingredient_recipe.breadBase as breadBase, " +
                " ingredient.name as name, " +
                " ingredient.brand as brand, " +
                " ingredient.unity as unity" +
                " FROM ingredient_recipe, ingredient  " +
                " WHERE id_recipe = ?" +
                " AND ingredient_recipe.id_ingredient = ?" +
                " AND ingredient_recipe.id_ingredient = ingredient.id_ingredient ", whereArgs);

        IngredientRecipeController ingredientRecipe = null;
        if (cursor.moveToFirst()) {
            do {
                ingredientRecipe = new IngredientRecipeController();
                ingredientRecipe.setIdIngredient(cursor.getInt(cursor.getColumnIndex("id_ingredient")));
                ingredientRecipe.setIdRecipe(cursor.getInt(cursor.getColumnIndex("id_recipe")));
                ingredientRecipe.setIngredientName(cursor.getString(cursor.getColumnIndex("name")));
                ingredientRecipe.setIngredientBrand(cursor.getString(cursor.getColumnIndex("brand")));
                ingredientRecipe.setUnity(cursor.getString(cursor.getColumnIndex("unity")));
                ingredientRecipe.setValue(cursor.getDouble(cursor.getColumnIndex("value")));
                ingredientRecipe.setQuantity(cursor.getDouble(cursor.getColumnIndex("quantity")));
                ingredientRecipe.setBreadBase( cursor.getInt(cursor.getColumnIndex("breadBase")) == 1);

            } while (cursor.moveToNext());
        }

        db.close();
        return ingredientRecipe;
    }

    public Long removeIngredientFromRecipe(Integer idRecipe, Integer idIngredient){
        long result;

        SQLiteDatabase db = banco.getWritableDatabase();
        String whereClause = "id_recipe = ? AND id_ingredient  = ?";
        String[] whereArgs = new String[] {idRecipe.toString(), idIngredient.toString()};
        result = db.delete("ingredient_recipe", whereClause, whereArgs );

        db.close();
        return result;

    }
}
