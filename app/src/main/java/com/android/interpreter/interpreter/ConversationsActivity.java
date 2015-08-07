package com.android.interpreter.interpreter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    private User current_user;

    private void handleConversations(String userId) {

        Firebase convRef = new Firebase(DBConnector.getPathToConversationsOf(userId));

        convRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                System.out.println("There are " + snapshot.getChildrenCount() + " messages");
                System.out.println("on data changeee");

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Conversation conversation = postSnapshot.getValue(Conversation.class);
                    conversations.add(conversation);
                    System.out.println(conversation.getReceivingLanguage()+ "     this is the receiving language  ");
                }
                mAdapter.notifyDataSetChanged();
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
                System.out.println(snapshot.getValue());

                current_user = snapshot.getValue(User.class);
// "email").getValue().toString(), snapshot.child("uid").getValue().toString(), snapshot.child("receivingLanguage").getValue().toString(),
//                        snapshot.child("sendingLanguage").getValue().toString(), snapshot.child("nickname").getValue().toString(), snapshot.child("GCMtoken").getValue(String.class));
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

                holder.last_message.setText("some message here");
                holder.date.setText("02-10-2015");
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
