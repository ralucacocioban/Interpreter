package com.android.interpreter.interpreter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences("myPrefs", 0);
        String currentUser = settings.getString("CURRENT_USER", null);
        System.out.println("current user in maina ctivity   " + currentUser);

        if(currentUser != null) {
            Intent convIntent = new Intent(this, ConversationsActivity.class);
            startActivity(convIntent);
        }
        else{
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        this.finish();
    }


}
