package com.example.final_project_water_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class loginPage extends AppCompatActivity {

    private EditText emailLogin, passwordLogin;
    private Button loginButton, registerPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerPage = (Button) findViewById(R.id.registerPage);
        emailLogin = (EditText) findViewById(R.id.emailLogin);
        passwordLogin = (EditText) findViewById(R.id.passwordLogin);

        String appID = "mobilefinal-kduua";
        App app = new App(new AppConfiguration.Builder(appID).build());

        Realm.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = emailLogin.getText().toString();
                String passwordText = passwordLogin.getText().toString();

                Credentials emailPasswordCredentials = Credentials.emailPassword(emailText, passwordText);
                AtomicReference<User> user = new AtomicReference<User>();
                app.loginAsync(emailPasswordCredentials, it -> {
                    if (it.isSuccess()) {
                        user.set(app.currentUser());
                        Toast.makeText(loginPage.this,"Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), homePage.class);
                        intent.putExtra("email", emailText);
                        intent.putExtra("pass", passwordText);
                        startActivity(intent);
                    } else {
                        String errorText = it.getError().toString();
                        String[] parts = errorText.split(" ");
                        String errorMessage = parts[parts.length - 2];
                        String errorMessage2 = parts[parts.length - 1];
                        Toast.makeText(loginPage.this,errorMessage + " " + errorMessage2, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        registerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}