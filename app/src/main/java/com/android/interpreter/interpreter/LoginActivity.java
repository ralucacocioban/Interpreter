package com.android.interpreter.interpreter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.interpreter.Config;
import com.android.interpreter.util.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    Button newUserButton;
    Button registerButton;
    Button cancelButton;
    TextView email;
    TextView password;
    TextView password2;
    Firebase rootRef;
    private User current_user;

    private String uId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        loginButton = (Button) findViewById(R.id.login_btn);
        email = (TextView) findViewById(R.id.email_input);
        password = (TextView) findViewById(R.id.password_input);
        password2 = (TextView) findViewById(R.id.password2_input);
        registerButton = (Button) findViewById(R.id.register_btn);
        newUserButton = (Button) findViewById(R.id.new_user_btn);
        cancelButton = (Button) findViewById(R.id.cancel_register_btn);
        Firebase.setAndroidContext(this);
        rootRef = new Firebase(Config.mainFireBaseRef);
    }

    public void newUser(View view) {
        password2.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        newUserButton.setVisibility(View.GONE);
        registerButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
    }

    public void register(View view) {
        if (!password.getText().toString().equals(password2.getText().toString())) {
            Toast.makeText(getBaseContext(), "Passwords are not identical", Toast.LENGTH_LONG).show();
        } else {
            rootRef.createUser(email.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {
                    Toast.makeText(getBaseContext(), "created user!", Toast.LENGTH_LONG).show();
                    System.out.println(result.get("uid"));

                    System.out.println("in login");
                    System.out.println("in login");
                    System.out.println("in login");
                    System.out.println("in login");
                    System.out.println("in login");

                    System.out.println("user before putting to preferences " + result.get("uid").toString());

                    Firebase ref = new Firebase(Config.mainFireBaseRef);

                    SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("CURRENT_USER", result.get("uid").toString());

                    Firebase userRef = new Firebase(DBConnector.getPathToUser(result.get("uid").toString()));

                    Firebase f = new Firebase(DBConnector.getPathToAllUsers());

                    current_user = new User(email.getText().toString(), result.get("uid").toString(), "", "", "", "");

                    f.push().setValue(current_user);
                    userRef.setValue(current_user);

                    editor.commit();
                    launchConfigPage();
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    Toast.makeText(getBaseContext(), "create failed!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void launchConfigPage() {
        Intent intent = new Intent(this, ConfigurationActivity.class);
        startActivity(intent);
    }

    private void launchConversations() {
        Intent intent = new Intent(this, ConversationsActivity.class);
        startActivity(intent);
    }


    public void login(View view) {

        rootRef.authWithPassword(
                email.getText().toString(), password.getText().toString(),

                new Firebase.AuthResultHandler() {

                    @Override
                    public void onAuthenticated(AuthData authData) {
                        System.out.println("User ID: " + authData.getUid() + ", Providerdfjhbdfdfjhbdfbjf: " + authData.getProvider());
                        Toast.makeText(getBaseContext(), "Login success!", Toast.LENGTH_LONG).show();

                        SharedPreferences settings = getSharedPreferences(Config.PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("CURRENT_USER", authData.getUid().toString());

                        Firebase userRef = new Firebase(DBConnector.getPathToUser(authData.getUid().toString()));

                        Firebase f = new Firebase(DBConnector.getPathToAllUsers());

                        current_user = new User(email.getText().toString(), authData.getUid().toString(), "", "", "", "");

                        f.push().setValue(current_user);
                        userRef.setValue(current_user);

                        editor.commit();
                        launchConfigPage();

                        launchConversations();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // there was an error
                        Toast.makeText(getBaseContext(), "Login failed!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void cancelRegister(View view) {
        password2.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
        newUserButton.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
    }
}
