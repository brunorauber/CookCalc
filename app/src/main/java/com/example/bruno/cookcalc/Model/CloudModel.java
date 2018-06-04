package com.example.bruno.cookcalc.Model;

import android.util.Log;

import com.example.bruno.cookcalc.Controller.IngredientController;
import com.example.bruno.cookcalc.Controller.RecipeController;
import com.example.bruno.cookcalc.Controller.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudModel {

    private DatabaseReference mDatabase;
    private DataSnapshot data;
    private HashMap<String, Object> db;

    public CloudModel() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot != null){
//                    data = dataSnapshot;
//                }
//                getSync();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void writeNewUser(String email, String state, String city, List<RecipeController> recipes, List<IngredientController> ingredients) {
        User user = new User(email, state, city, recipes, ingredients);
        email = email.replace(".", "!");
        //mDatabase.child("users").child(userId).setValue(user);
        mDatabase.child("users").child(state).child(city.toUpperCase()).
                child(email).setValue(user);
//        mDatabase.child("recipes").setValue(recipes);
    }


    public void getSync(String state, String city){
        System.out.println("SEFÇNWLENOFFIOw");
        System.out.println(state);
        System.out.println(city.toUpperCase());
        if(data != null) {
            System.out.println(data.child("users"));
        }
    }
    public void getSync(){
        if(data != null) {
            System.out.println(data.child("users"));
        }
    }

    public DataSnapshot getData() {
        return data;
    }
}
