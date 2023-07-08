package com.example.final_project_water_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class setGoal extends AppCompatActivity {

    Button calculateGoal;
    TextView recGoal;
    EditText goalAmount;
    Button backToHomeSG;
    Button setGoal;
    double calculatedConsumption = 0;
    double weight = 0;
    int age = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        Intent intent = getIntent();
        String emailText = intent.getStringExtra("email");
        String passwordText = intent.getStringExtra("pass");

        calculateGoal = findViewById(R.id.calculateGoal);
        recGoal = findViewById(R.id.recGoal);
        goalAmount = findViewById(R.id.goalAmount);
        backToHomeSG = findViewById(R.id.backToHomeSG);
        setGoal = findViewById(R.id.setGoal);

        String appID = "mobilefinal-kduua";
        App app = new App(new AppConfiguration.Builder(appID).build());

        Realm.init(this);
        Credentials emailPasswordCredentials = Credentials.emailPassword(emailText, passwordText);
        app.loginAsync(emailPasswordCredentials, it -> {
            User user = app.currentUser();
            MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
            MongoDatabase mongoDatabase = mongoClient.getDatabase("mobileFinalProject");
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("customUserData");

            Document queryFilter  = new Document("user-id-field", user.getId());
            mongoCollection.findOne(queryFilter).getAsync(task -> {
                Document result = task.get();
                age = Integer.parseInt(result.get("Age").toString());
                weight = Integer.parseInt(result.get("Weight").toString());
            });
        });

        calculateGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight = weight/2.2;
                if (age < 30) {
                    weight = weight * 40;
                }
                if (age > 55) {
                    weight = weight * 30;
                }
                if (age > 30 & age < 50) {
                    weight = weight * 35;
                }

                weight = weight / 28.3;
                calculatedConsumption = weight * 29.5735;

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recGoal.setText((Math.round(calculatedConsumption)) + "mL");
                        goalAmount.setText(String.valueOf(Math.round(calculatedConsumption)));
                    }
                }, 1000);
            }
        });

        setGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goal = goalAmount.getText().toString();
                app.loginAsync(emailPasswordCredentials, it -> {
                    User user = app.currentUser();
                    MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
                    MongoDatabase mongoDatabase = mongoClient.getDatabase("mobileFinalProject");
                    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("customUserData");
                    Document queryFilter  = new Document("user-id-field", user.getId());
                    Document updateDocument = new Document("$set", new Document("Goal", goal));
                    mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {});
                });
                Intent intent = new Intent(getApplicationContext(), homePage.class);
                intent.putExtra("email", emailText);
                intent.putExtra("pass", passwordText);
                startActivity(intent);
            }
        });

        backToHomeSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), homePage.class);
                intent.putExtra("email", emailText);
                intent.putExtra("pass", passwordText);
                startActivity(intent);
            }
        });
    }
}