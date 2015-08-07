package com.android.interpreter.interpreter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import com.android.interpreter.Helper;

public class ConfigurationActivity extends AbstractActivity {
    TextView nickName;
    EditText nickNameEdit;
    TextView sendingLanguage;
    Spinner sendingLanguageDropDown;
    TextView receivingLanguage;
    Spinner receivingLanguageDropDown;
    Button continueButton;
    Helper helper = new Helper();
    String sendingL;
    String receivingL;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
        String currentUser = settings.getString("CURRENT_USER", null);

        System.out.println("current user in Configuration Activity  " + currentUser);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        nickName = (TextView) findViewById(R.id.nickName);
        nickNameEdit = (EditText) findViewById(R.id.nickNameEdit);

        sendingLanguage = (TextView) findViewById(R.id.sendingLanguage);
        sendingLanguageDropDown = (Spinner) findViewById(R.id.sendingLanguageDropDown);
        receivingLanguage = (TextView) findViewById(R.id.receivingLanguage);
        receivingLanguageDropDown = (Spinner) findViewById(R.id.receivingLanguageDropDown);
        continueButton = (Button) findViewById(R.id.continueButton);

        ArrayAdapter<String> sendingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Config.sendLanguageArray);
        sendingLanguageDropDown.setAdapter(sendingAdapter);
        //int selectedLanguagePosition = helper.getDropdownLanguagePosition(currentUser.getSendingLanguage(), Config.sendLanguageArray);
        //sendingLanguageDropDown.setSelection(selectedLanguagePosition, false);
        sendingLanguageDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSendingLang = (String) parent.getItemAtPosition(position);
                sendingL = selectedSendingLang;
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
                String receivedReceivedLang = (String) parent.getItemAtPosition(position);
                receivingL = receivedReceivedLang;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //TODO: set selectedReceivedLang in DB if it is null in DB or Use previous from DB
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nickName = nickNameEdit.getText().toString();

                if(nickName != null){
//                    UserDetails details = new UserDetails(sendingL, receivingL, "", nickName);
//
//                    Firebase firebase = new Firebase(DBConnector.getPathToUser())
//                    Intent convIntent = new Intent(this, ConversationsActivity.class);
//                    startActivity(convIntent);
                }
            }
        });
    }

}
