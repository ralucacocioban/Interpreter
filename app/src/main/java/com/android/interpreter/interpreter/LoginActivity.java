package com.android.interpreter.interpreter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class LoginActivity extends ActionBarActivity {

    Button loginButton;
    Button newUserButton;
    Button registerButton;
    TextView email;
    TextView password;
    TextView password2;
    Firebase rootref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button)findViewById(R.id.login_btn);
        email = (TextView)findViewById(R.id.email_input);
        password = (TextView)findViewById(R.id.password_input);
        password2 = (TextView)findViewById(R.id.password2_input);
        registerButton = (Button)findViewById(R.id.register_btn);
        newUserButton = (Button)findViewById(R.id.new_user_btn);

        Firebase.setAndroidContext(this);
        rootref = new Firebase("https://flickering-heat-70.firebaseio.com/interpreter");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


    public void newUser(View view) {


        Toast.makeText(getBaseContext(), "register btn", Toast.LENGTH_LONG).show();
        password2.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.INVISIBLE);
        newUserButton.setVisibility(View.GONE);
        registerButton.setVisibility(View.VISIBLE);
    }

    public void register(View view){
        if (!password.getText().toString().equals(password2.getText().toString())){
            Toast.makeText(getBaseContext(), "Passwords are not identical" , Toast.LENGTH_LONG).show();
        }
        else {
            rootref.createUser(email.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {
                    Toast.makeText(getBaseContext(), "created user!", Toast.LENGTH_LONG).show();
                    System.out.println(result.get("uid"));
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    Toast.makeText(getBaseContext(), "create failed!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void login(View view){

        rootref.authWithPassword(
                email.getText().toString(), password.getText().toString(),

                new Firebase.AuthResultHandler() {

            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                Toast.makeText(getBaseContext(), "Login success!", Toast.LENGTH_LONG).show();
//                Intent toMainActivity = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(toMainActivity);
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                Toast.makeText(getBaseContext(), "Login failed!", Toast.LENGTH_LONG).show();            }
        });
    }
}
