package com.android.interpreter.interpreter;

import android.content.Intent;
import android.content.SharedPreferences;
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


        SharedPreferences settings = getSharedPreferences("CURRENT_USER", 0);
        boolean hasCurrentUser = settings.getBoolean("current_user", false);

        if(hasCurrentUser) {

            Intent convIntent = new Intent(this, ConversationsActivity.class);
            startActivity(convIntent);

        }
        else{


//            SharedPreferences.Editor editor = settings.edit();
//            editor.putBoolean("silentMode", mSilentMode);
//
//            // Commit the edits!
//            editor.commit();
//            Intent loginIntent = new Intent(this, ConversationsActivity.class);
//            startActivity(loginIntent);
        }

        this.finish();
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
