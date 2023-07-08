package com.example.final_project_water_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class profilePage extends AppCompatActivity {

    Spinner activityLevel;
    Button submitProfile;
    EditText name, age, gender, weight, height;
    String chosenActivityLevel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        String appID = "mobilefinal-kduua";
        App app = new App(new AppConfiguration.Builder(appID).build());

        Realm.init(this);

        activityLevel = findViewById(R.id.spinnerActivityLevel);
        submitProfile = findViewById(R.id.submitProfile);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);

        //create adapter to get array list from resources file
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(profilePage.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.activityLevel));
        //make it a dropdown
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //show the data in the spinner
        activityLevel.setAdapter(myAdapter);

        activityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //set the chosen level to a string
                chosenActivityLevel = activityLevel.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText = name.getText().toString();
                String ageText = age.getText().toString();
                String genderText = gender.getText().toString();
                String weightText = weight.getText().toString();
                String heightText = height.getText().toString();

                if(TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(age.getText()) || TextUtils.isEmpty(gender.getText()) || TextUtils.isEmpty(weight.getText()) || TextUtils.isEmpty(height.getText())){
                    Toast.makeText(profilePage.this,"Please fill out all fields.", Toast.LENGTH_LONG).show();
                }else{
                    //get all the data passed from other activity and assign them to variables
                    Intent receiverIntent = getIntent();
                    String emailText = receiverIntent.getStringExtra("email");
                    String passwordText = receiverIntent.getStringExtra("pass");
                    Credentials emailPasswordCredentials = Credentials.emailPassword(emailText, passwordText);
                    app.loginAsync(emailPasswordCredentials, it -> {
                        if (it.isSuccess()) {
                            User user = app.currentUser();
                            MongoClient mongoClient = user.getMongoClient("mongodb-atlas");
                            MongoDatabase mongoDatabase = mongoClient.getDatabase("mobileFinalProject");
                            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("customUserData");

                            mongoCollection.insertOne(new Document("user-id-field", user.getId()).append("Name", nameText).append("Age", ageText).append("Gender", genderText).append("Height", heightText).append("Weight", weightText).append("Activity Level", chosenActivityLevel).append("Goal", "0").append("Water Consumed", "0"))
                                    .getAsync(result -> {
                                        if (result.isSuccess()) {
                                            Log.v("EXAMPLE", "Inserted custom user data document. _id of inserted document: "
                                                    + result.get().getInsertedId());
                                        } else {
                                            Log.e("EXAMPLE", "Unable to insert custom user data. Error: " + result.getError());
                                        }
                                    });
                            Intent intent = new Intent(getApplicationContext(), homePage.class);
                            intent.putExtra("email", emailText);
                            intent.putExtra("pass", passwordText);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}