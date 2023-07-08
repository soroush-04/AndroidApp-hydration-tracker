package com.example.final_project_water_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button registerButton, loginPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerButton = (Button) findViewById(R.id.registerButton);
        loginPage = (Button) findViewById(R.id.loginPage);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        String appID = "mobilefinal-kduua";
        App app = new App(new AppConfiguration.Builder(appID).build());

        Realm.init(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                app.getEmailPassword().registerUserAsync(emailText, passwordText, it -> {
                    if (it.isSuccess()) {
                        Toast.makeText(MainActivity.this,"Registration Successful", Toast.LENGTH_LONG).show();
                        Intent sender = new Intent(getApplicationContext(), profilePage.class);
                        sender.putExtra("email", emailText);
                        sender.putExtra("pass", passwordText);
                        startActivity(sender);
                    } else {
                        Log.e("EXAMPLE", "Failed to register user: " + it.getError().getErrorMessage());
                        String errorText = it.getError().toString();
                        String[] parts = errorText.split(" ");
                        String errorMessage = parts[parts.length - 4];
                        String errorMessage2 = parts[parts.length - 3];
                        String errorMessage3 = parts[parts.length - 2];
                        String errorMessage4 = parts[parts.length - 1];
                        Toast.makeText(MainActivity.this,errorMessage + " " + errorMessage2 + " " + errorMessage3 + " " + errorMessage4, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), loginPage.class);
                startActivity(intent);
            }
        });
    }
}