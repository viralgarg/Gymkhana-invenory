package com.jeeteshgupta.gymkhanainventory;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GymkhanaInventory extends AppCompatActivity {

    private String council;
    private SharedPreferences sharedPreferences;
    private ArrayList<CoordinatorObject> coordinatorObjects = new ArrayList<>();
    private RecyclerView recyclerView;
    private CoordinatorListAdapter adapter;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymkhana_inventory);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        if(getIntent().hasExtra("Council")){
            council = getIntent().getStringExtra("Council").toString();
        }else{
            council = "Sports";
        }

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Coordinators");
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren() ){
                    CoordinatorObject object =  snapshot.getValue(CoordinatorObject.class);
                    coordinatorObjects.add(object);
                }

                ArrayList<CoordinatorObject> myCoordinators = new ArrayList<>();

                for( CoordinatorObject object : coordinatorObjects ){
                    if(object.getCouncil().equals(council)){
                        myCoordinators.add(object);
                    }
                }

                recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(GymkhanaInventory.this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setHasFixedSize(true);

                adapter = new CoordinatorListAdapter(GymkhanaInventory.this,myCoordinators);
                spinner.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
