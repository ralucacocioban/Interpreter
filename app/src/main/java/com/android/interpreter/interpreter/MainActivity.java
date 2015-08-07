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

        Firebase.setAndroidContext(this);
//        final Firebase userRef = new Firebase("https://flickering-heat-70.firebaseio.com/interpreter/user/simplelogin:25");

        final Firebase userRef = new Firebase(DBConnector.getPathToUser("simplelogin:25"));

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("BUGBUG", "sendingLanguage ref : " + dataSnapshot.child("sendingLanguage").getRef());

                Log.d("BUGBUG", "sendingLanguage is : " + dataSnapshot.child("sendingLanguage").getValue());


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        this.finish();
    }


}
