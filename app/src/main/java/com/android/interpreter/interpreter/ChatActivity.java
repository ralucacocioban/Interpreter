package com.android.interpreter.interpreter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.android.interpreter.Config;
import com.android.interpreter.util.Message;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This Activity represents the screen with one Chat.
 */
public class ChatActivity extends AbstractActivity {

    public final static String SENDER_ID = "sender";
    public final static String RECEIVER_ID = "receiver";

    // Parts needed for the UI, where all the messages are stored in 'messages'.
    private ListView messageList;
    private MessageListAdapter messageListAdapter;
    private ArrayList<Message> messages = new ArrayList<>();

    // This will be the format we will use at the bottom of the message, displaying the date.
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    // Reference in Firebase we start.
    Firebase conversationHereRef;
    Firebase conversationOtherRef;

    Message newMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageList = (ListView) findViewById(R.id.messages);
        messageListAdapter = new MessageListAdapter();
        messageList.setAdapter(messageListAdapter);

        // Setting up the correct root reference, giving a Conversation.
        Firebase.setAndroidContext(this);
        String senderID = getIntent().getStringExtra(SENDER_ID);
        String receiverID = getIntent().getStringExtra(RECEIVER_ID);
        conversationHereRef = new Firebase (DBConnector.getPathToMessages(senderID, receiverID));
        conversationOtherRef = new Firebase (DBConnector.getPathToMessages(receiverID, senderID));

        conversationHereRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // TODO - optimize this if needed
                Message current;
                ArrayList<Message> newmessages = new ArrayList<>((int) snapshot.getChildrenCount());
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    current = messageSnapshot.getValue(Message.class);
                    newmessages.add(current);
                }

                messages = newmessages;

                messageListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


    public void sendMessage(View view) {

        newMessage = new Message();
        EditText et = (EditText) findViewById(R.id.new_message);
        newMessage.setMessage(String.valueOf(et.getText()));
        newMessage.setDate(new Date());

        SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
        final String currentUserID = settings.getString("CURRENT_USER", null);
        newMessage.setSenderID(currentUserID);

        Firebase userRef = new Firebase(DBConnector.getPathToUser(currentUserID));
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newMessage.setOriginalLanguage((String) dataSnapshot.child("sendingLanguage").getValue());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        conversationHereRef.push().setValue(newMessage);
        conversationOtherRef.push().setValue(newMessage);

        // Delete the sent text from the EditText
        et.setText("");
    }


    /**
     * MessageListAdapter which handles the representation of the messages.
     */
    private class MessageListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Object getItem(int position) {
            return messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);

                // Here we get the currentUserID and then compare it with the sender of the message.
                SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
                final String currentUser = settings.getString("CURRENT_USER", null);

                // When the current user is the sender of this message, the message is placed right.
                if (currentUser.equals(messages.get(position).getSenderID())) {
                    view = inflater.inflate(R.layout.message_outgoing, null);
                }
                // Otherwise, when the current user is the receiver, the message is placed left
                else {
                    view = inflater.inflate(R.layout.message_incoming, null);
                }

                holder = new ViewHolder();
                holder.content = (TextView) view.findViewById(R.id.content);
                holder.date = (TextView) view.findViewById(R.id.date);
                view.setTag(holder);

                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        //TODO - create beautifull transition to message details
                        Intent showDetails = new Intent(ChatActivity.this, MessageDetailsActivity.class);
                        showDetails.putExtra(MessageDetailsActivity.ORIGINAL_LANGUAGE, messages.get(position).getOriginalLanguage());
                        showDetails.putExtra(MessageDetailsActivity.ORIGINAL_CONTENT, messages.get(position).getMessage());
                        // TODO - Get the translate language and translated content
                        showDetails.putExtra(MessageDetailsActivity.TRANSLATE_LANGUAGE, "");
                        showDetails.putExtra(MessageDetailsActivity.TRANSLATE_CONTENT, "");
                        // TODO - discuss whether we want the Date of the message as well in the detailled view.

                        startActivity(showDetails);

                        return true;
                    }
                });

            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }


            // Assign values to the view
            Message current = messages.get(position);
            // TODO - Get the translated message shown (not stored in DB)
            holder.content.setText(current.getMessage());
            holder.date.setText(df.format(current.getDate()));          // more info on df : top of the class

            return view;
        }

        // This class is used to create the message squares.
        private class ViewHolder {
            public TextView content;
            public TextView date;
        }
    }


}
