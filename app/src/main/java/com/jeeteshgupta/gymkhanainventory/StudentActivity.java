package com.jeeteshgupta.gymkhanainventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

public class StudentActivity extends AppCompatActivity {

    private Button sports , robotics , cultural , myinventory ,logout;
    private SharedPreferences sharedPreferences;

    private StudentObject studentObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        sports   = (Button) findViewById(R.id.sports);
        robotics = (Button) findViewById(R.id.robotics);
        cultural = (Button) findViewById(R.id.cultural);
        myinventory = (Button) findViewById(R.id.myinvnentory);
        logout   = (Button) findViewById(R.id.logoutbtn);

        sharedPreferences = this.getSharedPreferences("data",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("object","");
        studentObject = gson.fromJson(json,StudentObject.class);

        Toast.makeText(getBaseContext() , "Welcome "+studentObject.getName() , Toast.LENGTH_SHORT).show();

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, GymkhanaInventory.class ).putExtra("Council" , "Sports"));
            }
        });

        robotics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this , GymkhanaInventory.class).putExtra("Council","Robotics"));
            }
        });

        cultural.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this , GymkhanaInventory.class).putExtra("Council","Cultural"));
            }
        });

        myinventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this , StudentInventory.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putString("object","").commit();
                sharedPreferences.edit().putString("type","").commit();
                sharedPreferences.edit().putString("login","no").commit();

                startActivity(new Intent(StudentActivity.this,MainActivity.class));
            }
        });

    }
}
