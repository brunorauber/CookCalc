package com.example.bruno.cookcalc.Model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bruno.cookcalc.Controller.ConfigController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigModel {
    private CreateDataBase banco;

    public ConfigModel(Context context){
        banco = new CreateDataBase(context);
    }

    public Long insertConfig(String name, String value){
        ContentValues values;
        long result;
        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("name", name);
        values.put("value", value);
        result = db.insert("config", null, values);
        db.close();
        return result;
    }

    public Long updateConfig(String name, String value){
        ContentValues values;
        long result;
        SQLiteDatabase db = banco.getWritableDatabase();
        values = new ContentValues();
        values.put("value", value);
        String whereClause = " name = ?";
        String[] whereArgs = new String[] {name};
        result = db.update("config", values, whereClause, whereArgs);
        db.close();
        return result;
    }


    public ConfigController selectConfigById(Integer idConfig){
        String[] fields = {
                "id_config",
                "name",
                "value"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        String whereClause = "id_config = ?";
        String[] whereArgs = new String[] {idConfig.toString()};
        Cursor cursor = db.query("config", fields, whereClause, whereArgs, null, null, null);

        ConfigController config = null;
        if (cursor.moveToFirst()) {
            do {
                config = new ConfigController();
                config.setIdConfig(cursor.getInt(cursor.getColumnIndex("id_config")));
                config.setName(cursor.getString(cursor.getColumnIndex("name")));
                config.setValue(cursor.getString(cursor.getColumnIndex("value")));
            } while (cursor.moveToNext());
        }

        return config;
    }

    public ConfigController selectConfigByName(String nameConfig){
        String[] fields = {
                "id_config",
                "name",
                "value"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        String whereClause = "name = ?";
        String[] whereArgs = new String[] {nameConfig};
        Cursor cursor = db.query("config", fields, whereClause, whereArgs, null, null, null);;

        ConfigController config = null;
        if (cursor.moveToFirst()) {
            do {
                config = new ConfigController();
                config.setIdConfig(cursor.getInt(cursor.getColumnIndex("id_config")));
                config.setName(cursor.getString(cursor.getColumnIndex("name")));
                config.setValue(cursor.getString(cursor.getColumnIndex("value")));
            } while (cursor.moveToNext());
        }

        return config;
    }

    public Boolean configExists(String name){
        String[] fields = {
                "id_config",
                "name",
                "value"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        String whereClause = "name = ?";
        String[] whereArgs = new String[] {name};
        Cursor cursor = db.query("config", fields, whereClause, whereArgs, null, null, null);
        return cursor.moveToFirst();
    }

    public Map<String, String> listConfigs(){
        String[] fields = {
                "id_config",
                "name",
                "value"
        };
        SQLiteDatabase db = banco.getReadableDatabase();
        Map<String, String> configs = new HashMap<>();
        ConfigController config = null;
        Cursor cursor = db.query("config", fields,
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String value = cursor.getString(cursor.getColumnIndex("value"));
                configs.put(name, value);
            } while (cursor.moveToNext());
        }
        db.close();
        return configs;
    }


}
