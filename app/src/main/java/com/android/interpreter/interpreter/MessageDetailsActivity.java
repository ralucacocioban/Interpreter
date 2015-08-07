package com.android.interpreter.interpreter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


// So maybe we want to be it an Alert?
public class MessageDetailsActivity extends Activity {

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

}
