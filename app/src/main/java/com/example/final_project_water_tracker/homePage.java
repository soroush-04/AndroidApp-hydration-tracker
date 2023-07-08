package com.example.final_project_water_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class homePage extends AppCompatActivity {

    TextView welcome;
    TextView goal;
    TextView progressAmnt;
    Button setGoalBtn;
    ProgressBar progressBar;
    Button addWaterBtn;
    Button remindersBtn;
    Button profileBtn;
    Button logoutBtn;
    EditText addWaterInput;
    int waterConsumed = 0;
    String waterConsumedText = "";
    String updateWaterText = "";
    int goalForProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        String emailText = intent.getStringExtra("email");
        String passwordText = intent.getStringExtra("pass");

        welcome = findViewById(R.id.welcome);
        goal = findViewById(R.id.goal);
        setGoalBtn = findViewById(R.id.setGoalBtn);
        progressBar = findViewById(R.id.progressBar);
        addWaterBtn = findViewById(R.id.addWaterBtn);
        remindersBtn = findViewById(R.id.remindersBtn);
        profileBtn = findViewById(R.id.profileBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        addWaterInput = findViewById(R.id.addWaterInput);
        progressAmnt = findViewById(R.id.progressAmnt);

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
                goalForProgress = Integer.parseInt(result.get("Goal").toString());
                goal.setText("Goal: " + result.get("Goal").toString() + " mL");
                progressAmnt.setText((result.get("Water Consumed").toString() + " mL"));
                updateWaterText = result.get("Water Consumed").toString();

                progressBar.setMax(goalForProgress);
                progressBar.setProgress(Integer.parseInt(updateWaterText));
            });
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), profilePage2.class);
                intent1.putExtra("email", emailText);
                intent1.putExtra("pass", passwordText);
                startActivity(intent1);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), loginPage.class);
                startActivity(intent1);
            }
        });
        remindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), NotificationActivity.class);
                intent1.putExtra("email", emailText);
                intent1.putExtra("pass", passwordText);
                startActivity(intent1);
            }
        });
        setGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setGoal.class);
                intent.putExtra("email", emailText);
                intent.putExtra("pass", passwordText);
                startActivity(intent);
            }
        });

        addWaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addWaterText = addWaterInput.getText().toString();
                int addWaterNumber = Integer.parseInt(addWaterText);
                int updateWater = Integer.parseInt(updateWaterText);

                waterConsumed = updateWater + addWaterNumber;
                waterConsumedText = String.valueOf(waterConsumed);
                progressBar.setProgress(waterConsumed);
                app.loginAsync(emailPasswordCredentials, it -> {
                    User user = app.currentUser();
                    MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
                    MongoDatabase mongoDatabase = mongoClient.getDatabase("mobileFinalProject");
                    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("customUserData");
                    Document queryFilter  = new Document("user-id-field", user.getId());
                    Document updateDocument = new Document("$set", new Document("Water Consumed", waterConsumedText));
                    mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {});
                });
                progressAmnt.setText(waterConsumedText + " mL");
            }
        });
    }
}