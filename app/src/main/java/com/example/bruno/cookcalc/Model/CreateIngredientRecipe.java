package com.example.bruno.cookcalc.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateIngredientRecipe extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "cookcalc.db";
    private static final int VERSAO = 1;

    public CreateIngredientRecipe(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ingredient_recipe(" +
                "\n id_ingredient INTEGER, " +
                "\n id_recipe INTEGER," +
                "\n value REAL, " +
                "\n quantity REAL, " +
                "\n PRIMARY KEY (id_ingredient, id_recipe)" +
                "\n )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ingredient_recipe");
        onCreate(db);
    }
}
