package com.example.final_project_water_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class profilePage2 extends AppCompatActivity {

    private Button goHome, updateProfile;
    private EditText updateName, updateAge, updateGender, updateHeight, updateWeight;
    private TextView displayName, displayAge, displayGender, displayHeight, displayWeight, displaySpinner;
    Spinner updateActivityLevel;
    String chosenActivityLevel = "";
    Boolean selectedActivityLevel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page2);

        Intent intent = getIntent();
        String emailText = intent.getStringExtra("email");
        String passwordText = intent.getStringExtra("pass");

        goHome = findViewById(R.id.goHome);
        updateProfile = findViewById(R.id.updateProfile);

        updateName = findViewById(R.id.updateName);
        updateAge = findViewById(R.id.updateAge);
        updateGender = findViewById(R.id.updateGender);
        updateHeight = findViewById(R.id.updateHeight);
        updateWeight = findViewById(R.id.updateWeight);

        displayName = findViewById(R.id.displayName);
        displayAge = findViewById(R.id.displayAge);
        displayGender = findViewById(R.id.displayGender);
        displayHeight = findViewById(R.id.displayHeight);
        displayWeight = findViewById(R.id.displayWeight);
        displaySpinner = findViewById(R.id.displaySpinner);

        updateActivityLevel = findViewById(R.id.updateSpinnerActivityLevel);
        //create adapter to get array list from resources file
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(profilePage2.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.activityLevel));
        //make it a dropdown
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //show the data in the spinner
        updateActivityLevel.setAdapter(myAdapter);

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
                displayName.setText("Name: " + result.get("Name").toString());
                displayAge.setText("Age: " + result.get("Age").toString());
                displayGender.setText("Gender: " + result.get("Gender").toString());
                displayHeight.setText("Height: " + result.get("Height").toString());
                displayWeight.setText("Weight: " + result.get("Weight").toString());
                displaySpinner.setText("Activity Level: " + result.get("Activity Level").toString());
            });
        });

        updateActivityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //set the chosen level to a string
                chosenActivityLevel = updateActivityLevel.getSelectedItem().toString();
                selectedActivityLevel = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Credentials emailPasswordCredentials = Credentials.emailPassword(emailText, passwordText);
                app.loginAsync(emailPasswordCredentials, it -> {
                    User user = app.currentUser();
                    MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
                    MongoDatabase mongoDatabase = mongoClient.getDatabase("mobileFinalProject");
                    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("customUserData");
                    Document queryFilter  = new Document("user-id-field", user.getId());

                    String updateNameText = updateName.getText().toString();
                    String updateAgeText = updateAge.getText().toString();
                    String updateGenderText = updateGender.getText().toString();
                    String updateHeightText = updateHeight.getText().toString();
                    String updateWeightText = updateWeight.getText().toString();

                    if (updateNameText.matches("")){
                        //empty edit text so do nothing
                    } else {
                        Document updateDocument = new Document("$set", new Document("Name", updateNameText));
                        mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {});
                    }

                    if (updateAgeText.matches("")){
                        //empty edit text so do nothing
                    } else {
                        Document updateDocument = new Document("$set", new Document("Age", updateAgeText));
                        mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {});
                    }

                    if (updateGenderText.matches("")){
                        //empty edit text so do nothing
                    } else {
                        Document updateDocument = new Document("$set", new Document("Gender", updateGenderText));
                        mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {});
                    }

                    if (updateHeightText.matches("")){
                        //empty edit text so do nothing
                    } else {
                        Document updateDocument = new Document("$set", new Document("Height", updateHeightText));
                        mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {});
                    }

                    if (updateWeightText.matches("")){
                        //empty edit text so do nothing
                    } else {
                        Document updateDocument = new Document("$set", new Document("Weight", updateWeightText));
                        mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {});
                    }

                    if (selectedActivityLevel = true){
                        Document updateDocument = new Document("$set", new Document("Activity Level", chosenActivityLevel));
                        mongoCollection.updateOne(queryFilter, updateDocument).getAsync(task -> {});
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mongoCollection.findOne(queryFilter).getAsync(task -> {
                                Document result = task.get();
                                displayName.setText("Name: " + result.get("Name").toString());
                                displayAge.setText("Age: " + result.get("Age").toString());
                                displayGender.setText("Gender: " + result.get("Gender").toString());
                                displayHeight.setText("Height: " + result.get("Height").toString());
                                displayWeight.setText("Weight: " + result.get("Weight").toString());
                                displaySpinner.setText("Activity Level: " + result.get("Activity Level").toString());
                            });

                        }
                    }, 1000);
                });
            }
        });

        goHome.setOnClickListener(new View.OnClickListener() {
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