package com.android.interpreter.interpreter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.interpreter.interpreter.R;

import java.util.ArrayList;

public class SearchActivity extends ActionBarActivity {

    private TextView result;
    private Button initiateChat;
    ArrayList<String> allUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
                }
                else {  // Sorry sorry.
                    result.setText("We are fairly sorry, try again.");
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
}
