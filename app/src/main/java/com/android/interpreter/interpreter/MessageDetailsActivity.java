package com.android.interpreter.interpreter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.interpreter.interpreter.R;

public class MessageDetailsActivity extends ActionBarActivity {

    public static final String ORIGINAL_LANGUAGE = "original language";
    public static final String ORIGINAL_CONTENT = "original content";
    public static final String TRANSLATE_LANGUAGE = "translate language";
    public static final String TRANSLATE_CONTENT = "translate content";

    private String originalLanguage;
    private String originalContent;
    private String translatedLanguage;
    private String translatedContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        TextView olView = (TextView) findViewById(R.id.original_language);
        TextView ocView = (TextView) findViewById(R.id.original_content);
        TextView tlView = (TextView) findViewById(R.id.translate_language);
        TextView tcView = (TextView) findViewById(R.id.translate_content);

        olView.setText(getIntent().getStringExtra(ORIGINAL_LANGUAGE));
        ocView.setText(getIntent().getStringExtra(ORIGINAL_CONTENT));
        tlView.setText(getIntent().getStringExtra(TRANSLATE_LANGUAGE));
        tcView.setText(getIntent().getStringExtra(TRANSLATE_CONTENT));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_details, menu);
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
