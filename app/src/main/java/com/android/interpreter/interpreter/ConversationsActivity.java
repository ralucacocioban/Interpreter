package com.android.interpreter.interpreter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.interpreter.Config;
import com.android.interpreter.util.Conversation;
import com.android.interpreter.util.Message;
import com.android.interpreter.util.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by ralucamelon on 06/08/2015.
 */
public class ConversationsActivity extends AbstractActivity {
    ImageButton imageButton;
    private ListView mList;
    private MyAdapter mAdapter;
    private List<Conversation> conversations = new ArrayList<>();
    private List<User> receivers = new ArrayList<>();
    private User current_user;
    private ArrayList<Message> messages = new ArrayList<>();
    private String[] names = {"Mike", "Andrea", "Suzana", "Maria", "Ion", "John"};

    Firebase convsRef;
    Firebase userRef;

    private void handleConversations(String userId) {

        Firebase convRef = new Firebase(DBConnector.getPathToConversationsOf(userId));

        convRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Conversation current;
                ArrayList<Conversation> newConversations = new ArrayList<Conversation>((int) snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    current = postSnapshot.getValue(Conversation.class);
                    attachReceiverInfo(current.getReceiverId());
                    newConversations.add(current);
                }
                conversations = newConversations;
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void attachReceiverInfo(String userId) {

        userRef = new Firebase(DBConnector.getPathToUser(userId));

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                System.out.println("There are " + snapshot.getChildrenCount() + " messages");
                System.out.println("in user info ");
                User user = snapshot.getValue(User.class);
                receivers.add(user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void attachLastMessage(String senderId, String receiverId) {

        convsRef = new Firebase(DBConnector.getPathToMessages(senderId, receiverId));
        convsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    messages.add(snap.getValue(Message.class));
                    System.out.println("messages here");
                    System.out.println("messages here");
                    System.out.println("messages here");
                    System.out.println(snap.getValue(Message.class));
                    break;
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
        setContentView(R.layout.conversations);

        mList = (ListView) findViewById(R.id.conversationsList);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversation conversationClicked = (Conversation) mList.getItemAtPosition(position);
                Intent intent = new Intent(ConversationsActivity.this, ChatActivity.class);
                intent.putExtra(ChatActivity.SENDER_ID, current_user.getUid());
                intent.putExtra(ChatActivity.RECEIVER_ID, conversationClicked.getReceiverId());
                startActivity(intent);
            }
        });

        imageButton = (ImageButton)findViewById(R.id.logo);
        imageButton.setVisibility(View.INVISIBLE);

        mAdapter = new MyAdapter();
        mList.setAdapter(mAdapter);
        Firebase.setAndroidContext(this);
        Button btn = (Button) findViewById(R.id.addConversation);

        SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
        final String currentUser = settings.getString("CURRENT_USER", null);

        Firebase userRef = new Firebase(DBConnector.getPathToUser(currentUser));

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("FACKING DUCKS");
                System.out.println(snapshot.getValue());
                current_user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        handleConversations(currentUser);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConversationsActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//
//        setContentView(R.layout.conversations);
//
//        mList = (ListView) findViewById(R.id.conversationsList);
//        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Conversation conversationClicked = (Conversation) mList.getItemAtPosition(position);
//                Intent intent = new Intent(ConversationsActivity.this, ChatActivity.class);
//                intent.putExtra(ChatActivity.SENDER_ID, current_user.getUid());
//                intent.putExtra(ChatActivity.RECEIVER_ID, conversationClicked.getReceiverId());
//                startActivity(intent);
//            }
//        });
//
//        mAdapter = new MyAdapter();
//        mList.setAdapter(mAdapter);
//        Firebase.setAndroidContext(this);
//        Button btn = (Button) findViewById(R.id.addConversation);
//
//        SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
//        final String currentUser = settings.getString("CURRENT_USER", null);
//
//        Firebase userRef = new Firebase(DBConnector.getPathToUser(currentUser));
//
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                System.out.println(snapshot.getValue());
//                current_user = snapshot.getValue(User.class);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
//            }
//        });
//
//
//        handleConversations(currentUser);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ConversationsActivity.this, SearchActivity.class);
//                startActivity(intent);
//            }
//        });

    }


    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return conversations.size();
        }

        @Override
        public Object getItem(int position) {
            return conversations.get(position);
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

                LayoutInflater inflater = LayoutInflater.from(ConversationsActivity.this);
                view = inflater.inflate(R.layout.conversation_item, null);

                holder = new ViewHolder();
                holder.receiver = (TextView) view.findViewById(R.id.receiver);
                holder.last_message = (TextView) view.findViewById(R.id.last_message);
                holder.date = (TextView) view.findViewById(R.id.date);
                holder.profile_img = (ImageView) view.findViewById(R.id.profile_img);
                view.setTag(holder);

            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            Conversation current = conversations.get(position);

            if (current != null) {

                holder.last_message.setText("Use sending language: " + current.getSendingLanguage());
                holder.date.setText(new Date().toString());
                Random randomGenerator;

                String randomString = names[(int) (Math.random() * names.length)];
                holder.receiver.setText(randomString);


//                holder.date.setText("02-10-2015");
                holder.profile_img.setImageResource(R.drawable.no_user);
            }

            return view;
        }
    }

    private class ViewHolder {
        public TextView receiver;
        public TextView last_message;
        public TextView date;
        public ImageView profile_img;
    }

}