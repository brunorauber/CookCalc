package com.example.bruno.cookcalc.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateIngredient extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "cookcalc.db";
    private static final int VERSAO = 1;

    public CreateIngredient(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ingredient(" +
                "id_ingredient INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "value REAL, " +
                "unity TEXT, " +
                "quantity REAL" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ingredient");
        onCreate(db);
    }
}
