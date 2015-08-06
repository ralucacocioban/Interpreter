package com.android.interpreter.interpreter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.interpreter.Config;
import com.android.interpreter.Helper;

public class ConfigurationMessageActivity extends ActionBarActivity {
    TextView text;
    TextView sendingLanguage;
    Spinner sendingLanguageDropDown;
    TextView receivingLanguage;
    Spinner receivingLanguageDropDown;
    Button saveButton;
    Helper helper = new Helper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_message);

        text = (TextView)findViewById(R.id.text);
        sendingLanguage = (TextView)findViewById(R.id.sendingLanguage);
        sendingLanguageDropDown = (Spinner)findViewById(R.id.sendingLanguageDropDown);
        receivingLanguage = (TextView)findViewById(R.id.receivingLanguage);
        receivingLanguageDropDown = (Spinner)findViewById(R.id.receivingLanguageDropDown);
        saveButton = (Button)findViewById(R.id.saveButton);

        //TODO: get currentUser conversation
//        User currentUser = new User();
//
        ArrayAdapter<String> sendingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Config.sendLanguageArray);
        sendingLanguageDropDown.setAdapter(sendingAdapter);
//        int selectedLanguagePosition = helper.getDropdownLanguagePosition(currentUser.getSendingLanguage(), Config.sendLanguageArray);
//        sendingLanguageDropDown.setSelection(selectedLanguagePosition, false);
        sendingLanguageDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSendingLang = (String) parent.getItemAtPosition(position);
                //TODO: set selectedSendingLang in DB
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: set selectedSendingLang in DB if it is null in DB or Use previous from DB
            }
        });

        ArrayAdapter<String> receivingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Config.receiveLanguageArray);
        receivingLanguageDropDown.setAdapter(receivingAdapter);
//        int receivedLanguagePosition = helper.getDropdownLanguagePosition(currentUser.getSendingLanguage(), Config.sendLanguageArray);
//        receivingLanguageDropDown.setSelection(receivedLanguagePosition, false);
        receivingLanguageDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedReceivedLang = (String) parent.getItemAtPosition(position);
                //TODO: set selectedReceivedLang in DB
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: set selectedReceivedLang in DB if it is null in DB or Use previous from DB
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: call intent with conversations
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration_message, menu);
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
