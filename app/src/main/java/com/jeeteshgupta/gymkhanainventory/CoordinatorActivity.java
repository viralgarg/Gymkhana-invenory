package com.jeeteshgupta.gymkhanainventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class CoordinatorActivity extends AppCompatActivity implements Serializable{

    Button chngpassword,logout,scan,modifyinvetory , record;

    private CoordinatorObject coordinatorObject;
    private String council;

    private SharedPreferences sharedPreferences;

    private ArrayList<Inventory> inventory;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        sharedPreferences = this.getSharedPreferences("data",MODE_PRIVATE);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        //Changing json data...
        Gson gson = new Gson();
        String json = sharedPreferences.getString("object","");
        coordinatorObject = gson.fromJson(json,CoordinatorObject.class);

        modifyinvetory=(Button) findViewById(R.id.modifyinventory);
        modifyinvetory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                Intent intent = new Intent(CoordinatorActivity.this,AddInventory.class);
                spinner.setVisibility(View.GONE);
                startActivity(intent);
            }
        });

        scan=(Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                Intent intent = new Intent(CoordinatorActivity.this, Scan.class);
                spinner.setVisibility(View.GONE);
                startActivity(intent);
            }
        });

        record = (Button) findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                Intent intent = new Intent(CoordinatorActivity.this , CoordinatorRecord.class);
                spinner.setVisibility(View.GONE);
                startActivity(intent);
            }
        });

        logout = (Button) findViewById(R.id.logoutbtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                sharedPreferences.edit().putString("type" , "").commit();
                sharedPreferences.edit().putString("object","").commit();
                sharedPreferences.edit().putString("login","0").commit();

                spinner.setVisibility(View.GONE);
                startActivity(new Intent(CoordinatorActivity.this,MainActivity.class));
            }
        });

    }
}


