package com.example.alex.dg_chatbot;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by alex on 2018. 5. 7..
 */

public class FirebaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
