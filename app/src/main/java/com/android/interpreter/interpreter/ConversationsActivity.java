package com.android.interpreter.interpreter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.interpreter.Config;
import com.android.interpreter.util.Conversation;
import com.android.interpreter.util.User;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralucamelon on 06/08/2015.
 */
public class ConversationsActivity extends AbstractActivity {

    private ListView mList;
    private MyAdapter mAdapter;
    private List<Conversation> conversations = new ArrayList<>();
    private List<User> all_users = new ArrayList<>();

    private void getAllUsers(){

        Firebase usersRef = new Firebase(DBConnector.getPathToUsers());

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println(snapshot.getValue());
                System.out.println("There are " + snapshot.getChildrenCount() + " users");

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    all_users.add(user);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void handleConversations() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversations);

        getAllUsers();

        for(User u : all_users){
            System.out.println("all users  " + u.getEmail());
        }

        mList = (ListView) findViewById(R.id.conversationsList);
        mAdapter = new MyAdapter();
        mList.setAdapter(mAdapter);
        Firebase.setAndroidContext(this);
        Button btn = (Button) findViewById(R.id.addConversation);

        handleConversations();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("heyyyy");
                System.out.println("heyyyy");

                /// TODO: 06/08/2015
                // launch activity for searching other users
            }
        });

//        convRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
//                System.out.println(snapshot.getKey());
//                System.out.println("dfjkhdfjkdf in child added");
//                System.out.println("dfjkhdfjkdf in child added");
//                System.out.println("dfjkhdfjkdf in child added");
//                System.out.println("dfjkhdfjkdf in child added");
//
////                Conversation conversation = snapshot.getValue(Conversation.class);
////                conversations.add(conversation);
////                mAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
//
//
//        convRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                System.out.println(snapshot.getValue());
//
//                System.out.println("There are " + snapshot.getChildrenCount() + " messages");
//                System.out.println("on data changeee");
//
//
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    Conversation conversation = postSnapshot.getValue(Conversation.class);
//                    conversations.add(conversation);
//                    System.out.println(conversation.getTo() + "     this is the receiverrr  ");
//                }
//
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
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

//                holder.last_message.setText(current.getMessages().get(current.getMessages().size() - 1).getMessage());
//                holder.date.setText(current.getMessages().get(current.getMessages().size() - 1).getDate().toString());
//                Picasso.with(getBaseContext())
//                        .load(current.getPicUrl())
//                        .into(holder.img);
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
