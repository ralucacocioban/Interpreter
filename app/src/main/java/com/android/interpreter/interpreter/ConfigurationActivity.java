package com.android.interpreter.interpreter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.interpreter.interpreter.R;

import org.w3c.dom.Text;

public class ConfigurationActivity extends ActionBarActivity {
    TextView nickName;
    EditText nickNameEdit;
    TextView sendingLanguage;
    Spinner sendingLanguageDropDown;
    TextView receivingLanguage;
    Spinner receivingLanguageDropDown;
    Button continueButton;
    String[] sendLanguageArray = new String[]{"English", "Russian", "Ukrainian", "Polish", "Dutch", "Romanian"};
    String[] receiveLanguageArray = new String[]{"English", "Russian", "Ukrainian", "Polish", "Dutch", "Romanian"};

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

        ArrayAdapter<String> sendingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sendLanguageArray);
        sendingLanguageDropDown.setAdapter(sendingAdapter);

        ArrayAdapter<String> receivingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, receiveLanguageArray);
        receivingLanguageDropDown.setAdapter(receivingAdapter);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: call intent with conversations
            }
        });
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
