package com.android.interpreter.interpreter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This Activity represents the screen with one Chat.
 */
public class ChatActivity extends AppCompatActivity {

    // Parts needed for the UI, where all the messages are stored in 'messages'.
    private ListView messageList;
    private MessageListAdapter messageListAdapter;
    private ArrayList<Message> messages = new ArrayList<>();

    // This will be the format we will use at the bottom of the message, displaying the date.
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    // Reference in Firebase we start.
    Firebase conversationref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageList = (ListView) findViewById(R.id.messages);
        messageListAdapter = new MessageListAdapter();
        messageList.setAdapter(messageListAdapter);

        // Setting up the correct root reference, giving a Conversation.
        Firebase.setAndroidContext(this);
        conversationref = new Firebase("");     // TO DO PLACE THE CORRECT URL HERE.

        conversationref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                // It must be possible to optimize this!!
                Message current;
                ArrayList<Message> newmessages = new ArrayList<Message>((int) snapshot.getChildrenCount());
                for(DataSnapshot messageSnapshot : snapshot.getChildren()) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(ChatActivity.this);
                // When the current user is the sender of this message, the message is placed right.
                if (true) { // TO DO,  CHECK THIS!
                    view = inflater.inflate(R.layout.message_right, null);
                }
                // Otherwise, when the current user is the receiver, the message is placed left
                else {
                    view = inflater.inflate(R.layout.message_left, null);
                }

                holder = new ViewHolder();
                holder.content = (TextView) view.findViewById(R.id.content);
                holder.date = (TextView) view.findViewById(R.id.date);
                view.setTag(holder);

            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            // Assign values to the view
            Message current = messages.get(position);
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
