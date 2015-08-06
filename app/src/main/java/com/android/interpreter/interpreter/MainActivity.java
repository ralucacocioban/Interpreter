package com.android.interpreter.interpreter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mList;
    private MyAdapter mAdapter;
    private List<Message> all_messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(R.id.listView);
        mAdapter = new MyAdapter();
        mList.setAdapter(mAdapter);
        Firebase.setAndroidContext(this);
        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText text = (EditText) findViewById(R.id.message);
                String current_message = text.getText().toString();

                Firebase ref = new Firebase("https://flickering-heat-70.firebaseio.com/chat/messages");

                Message msg = new Message(current_message, new Date());

                //Map<String, String> messages = new HashMap<String, String>();
                //messages.put("text", current_message);

                ref.push().setValue(msg);
                all_messages.add(msg);
            }
        });


        Firebase ref = new Firebase("https://flickering-heat-70.firebaseio.com/chat/messages/");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                System.out.println(snapshot.getKey());
                System.out.println("dfjkhdfjkdf in child added");
                System.out.println("dfjkhdfjkdf in child added");
                System.out.println("dfjkhdfjkdf in child added");
                System.out.println("dfjkhdfjkdf in child added");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());

                System.out.println("There are " + snapshot.getChildrenCount() + " messages");
                System.out.println("on data changeee");

                System.out.println(snapshot.getKey());
                System.out.println(snapshot.getKey());
                System.out.println(snapshot.getKey());
                System.out.println(snapshot.getKey());


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Message messages = postSnapshot.getValue(Message.class);
                    System.out.println(messages.getMessage() + "    ");
                }



                //all_messages.add(snapshot.child("text").getValue().toString());

                mAdapter.notifyDataSetChanged();
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


    public class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return all_messages.size();
        }

        @Override
        public Object getItem(int position) {
            return all_messages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                tv = new TextView(MainActivity.this);
                tv.setText(all_messages.get(position).getMessage());
            } else {
                tv = (TextView) convertView;
                tv.setText(all_messages.get(position).getMessage());
            }
            tv.setPadding(16, 16, 16, 16);
            tv.setTextSize(22);
            return tv;
        }
    }


}
