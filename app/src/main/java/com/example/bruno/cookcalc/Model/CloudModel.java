package com.example.bruno.cookcalc.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class CloudModel {

    private DatabaseReference mDatabase;

    public CloudModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public Map<String, Object> getSync(){


        return null;
    }
}
