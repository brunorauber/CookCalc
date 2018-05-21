package com.example.bruno.cookcalc.Model;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDataBase extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "cookcalc.db";
    private static final int VERSAO = 1;

    public CreateDataBase(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }


    public CreateDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ingredient(" +
                "\n id_ingredient INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "\n name TEXT, " +
                "\n brand TEXT, " +
                "\n latest_value REAL, " +
                "\n unity TEXT, " +
                "\n last_update DATE, " +
                "\n quantity REAL" +
                "\n )";
        db.execSQL(sql);

        sql = "CREATE TABLE recipe(" +
                "\n id_recipe INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "\n name TEXT, " +
                "\n last_update DATE, " +
                "\n portions INTEGER, " +
                "\n minutes NUMERIC, " +
                "\n isBread NUMERIC, " +
                "\n isFavorite NUMERIC, " +
                "\n latest_value REAL" +
                ")";
        db.execSQL(sql);

        sql = "CREATE TABLE ingredient_recipe(" +
                "\n id_ingredient INTEGER, " +
                "\n id_recipe INTEGER," +
                "\n value REAL, " +
                "\n quantity REAL, " +
                "\n breadBase NUMERIC, " +
                "\n creation_date DATE, " +
                "\n PRIMARY KEY (id_ingredient, id_recipe)" +
                "\n )";
        db.execSQL(sql);

        sql = "CREATE TABLE ingredient_price(" +
                "\n id_price INTEGER PRIMARY KEY AUTOINCREMENT," +
                "\n id_ingredient INTEGER, " +
                "\n creation_date DATE, " +
                "\n value REAL " +
                "\n )";
        db.execSQL(sql);

        sql = "CREATE TABLE recipe_price(" +
                "\n id_price INTEGER PRIMARY KEY AUTOINCREMENT," +
                "\n id_recipe INTEGER, " +
                "\n creation_date DATE, " +
                "\n value REAL " +
                "\n )";
        db.execSQL(sql);

        sql = "CREATE TABLE config(" +
                "\n id_config INTEGER PRIMARY KEY AUTOINCREMENT," +
                "\n name TEXT, " +
                "\n value TEXT, " +
                "\n CONSTRAINT config_name_unique UNIQUE (name) " +
                " )";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ingredient");
        db.execSQL("DROP TABLE IF EXISTS recipe");
        db.execSQL("DROP TABLE IF EXISTS ingredient_recipe");
        db.execSQL("DROP TABLE IF EXISTS ingredient_price");
        db.execSQL("DROP TABLE IF EXISTS recipe_price");
        onCreate(db);

    }
}
