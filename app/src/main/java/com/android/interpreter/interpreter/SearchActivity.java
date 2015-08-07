package com.android.interpreter.interpreter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.interpreter.util.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AbstractActivity {

    private TextView result;
    private Button initiateChat;
    ArrayList<User> allUsers = new ArrayList<>();

    private void getAllUsers() {

        Firebase usersRef = new Firebase(DBConnector.getPathToUsers());

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                System.out.println("There are " + snapshot.getChildrenCount() + " users");

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    System.out.println("user here  " + postSnapshot.getValue().toString());
//                    User user = postSnapshot.getValue(User.class);
//                    allUsers.add(user);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        getAllUsers();

        for(User u : allUsers){
            System.out.println("user after assignment " + u.getEmail());
        }


        result = (TextView) findViewById(R.id.search_result);
        initiateChat = (Button) findViewById(R.id.initiate_button);

        // At first, the result text is empty and the initiate chat button does not exist.
        result.setText("");
        initiateChat.setClickable(false);
        initiateChat.setVisibility(View.INVISIBLE);

        initiateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // INITIATE NEW CHAT AND GO TO CHAT VIEW.
            }
        });

        // TODO - fill the arrayList with all the users.


        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String adress = String.valueOf(((EditText) findViewById(R.id.search_text)).getText());

                if (allUsers.contains(adress)) {     // Yes, he user exists.
                    // TODO - enhance information of the found user.
                    result.setText("Yes, we found someone, do you want to initiate chat?");
                    initiateChat.setVisibility(View.VISIBLE);
                    initiateChat.setClickable(true);
                } else {  // Sorry sorry.
                    result.setText("We are fairly sorry, try again.");
                }
            }
        });


    }

}
