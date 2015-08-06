package com.android.interpreter.interpreter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.interpreter.Config;
import com.android.interpreter.interpreter.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ConfigurationActivity extends ActionBarActivity {
    TextView nickName;
    EditText nickNameEdit;
    TextView sendingLanguage;
    Spinner sendingLanguageDropDown;
    TextView receivingLanguage;
    Spinner receivingLanguageDropDown;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        nickName = (TextView)findViewById(R.id.nickName);
        nickNameEdit = (EditText)findViewById(R.id.nickNameEdit);
        sendingLanguage = (TextView)findViewById(R.id.sendingLanguage);
        sendingLanguageDropDown = (Spinner)findViewById(R.id.sendingLanguageDropDown);
        receivingLanguage = (TextView)findViewById(R.id.receivingLanguage);
        receivingLanguageDropDown = (Spinner)findViewById(R.id.receivingLanguageDropDown);
        continueButton = (Button)findViewById(R.id.continueButton);

        ArrayAdapter<String> sendingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Config.sendLanguageArray);
        sendingLanguageDropDown.setAdapter(sendingAdapter);
        //TODO: show already chosen language in dropdown. Should be the same for receivingLanguageDropDown
        //int selectedLanguagePosition = getDropdownLanguagePosition(currentUserLanguage, Config.sendLanguageArray);
        //sendingLanguageDropDown.setSelection(position, false);
        sendingLanguageDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedSendingLang = Config.sendLanguageArray[position];
                Log.v("item", (String) parent.getItemAtPosition(position));
                //TODO: set selectedSendingLang in DB
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: set selectedSendingLang in DB if it is null in DB or Use previous from DB
            }
        });

        ArrayAdapter<String> receivingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Config.receiveLanguageArray);
        receivingLanguageDropDown.setAdapter(receivingAdapter);
        receivingLanguageDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedReceivedLang = Config.receiveLanguageArray[position];
                //TODO: set selectedReceivedLang in DB
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: set selectedReceivedLang in DB if it is null in DB or Use previous from DB
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: call intent with conversations
            }
        });
    }

    public int getDropdownLanguagePosition(String currentUserLanguage, String [] languages) {
        //TODO: we can make it better, but array is not large
        for (int i = 0; i < languages.length; ++i) {
            if (languages[i] == currentUserLanguage) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
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
