package com.android.interpreter.interpreter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.interpreter.Config;
import com.android.interpreter.interpreter.R;
import com.android.interpreter.util.Message;
import com.android.interpreter.util.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import java.util.ArrayList;
import java.util.Date;

public class SearchActivity extends AbstractActivity {

    private TextView result;
    private Button initiateChat;
    ArrayList<User> allUsers = new ArrayList<>();
    private User wantedUser;

    private void getAllUsers() {

        Firebase usersRef = new Firebase(DBConnector.getPathToAllUsers());

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                System.out.println("There are " + snapshot.getChildrenCount() + " users");

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    System.out.println("user here  " + postSnapshot.getValue().toString());
                    User user = postSnapshot.getValue(User.class);
                    allUsers.add(user);
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

        result = (TextView) findViewById(R.id.search_result);
        initiateChat = (Button) findViewById(R.id.initiate_button);

        // At first, the result text is empty and the initiate chat button does not exist.
        result.setText("");
        initiateChat.setClickable(false);
        initiateChat.setVisibility(View.INVISIBLE);

        initiateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the ID of the sender and receiver.
                SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
                String senderID = settings.getString("CURRENT_USER", null);
                String receiverID = wantedUser.getUid();

                // Create a introduction message.
                Message firstMessage = new Message();
                firstMessage.setMessage("Hello, I want to chat with you!");
                firstMessage.setOriginalLanguage("en");
                firstMessage.setSenderID(senderID);
                firstMessage.setDate(new Date());

                System.out.println(firstMessage.getDate().toString());

                // We need to add the conversations (twice)
                Firebase conversationOfRef = new Firebase(DBConnector.getPathToConversationsOf(senderID));
                conversationOfRef.push().setValue(receiverID);
                conversationOfRef = new Firebase(DBConnector.getPathToConversationsOf(receiverID));
                conversationOfRef.push().setValue(senderID);

                // Now we add the first message to this conversation.
                Firebase chatRef = new Firebase(DBConnector.getPathToMessages(senderID, receiverID));
                chatRef.push().setValue(firstMessage);
                chatRef = new Firebase(DBConnector.getPathToMessages(receiverID, senderID));
                chatRef.push().setValue(firstMessage);

                // Now go to the chat screen.
                Intent toChat = new Intent(SearchActivity.this, ChatActivity.class);
                toChat.putExtra(ChatActivity.SENDER_ID, senderID);
                toChat.putExtra(ChatActivity.RECEIVER_ID, receiverID);
                startActivity(toChat);

            }
        });


        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                wantedUser = null;
                String adress = String.valueOf(((EditText) findViewById(R.id.search_text)).getText());

                for(User user : allUsers) {
                    if(user.getEmail().equals(adress)) {
                        wantedUser = user;
                    }
                }

                if (wantedUser != null) {     // Yes, he user exists.
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
