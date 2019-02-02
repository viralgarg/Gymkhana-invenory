package com.jeeteshgupta.gymkhanainventory;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class StudentInventory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    private String json;

    private StudentObject studentObject;
    private ArrayList<StudentInventoryRecord> studentInventoryRecords;
    StudentInventoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_inventory);

        recyclerView = findViewById(R.id.recyclerView);
        sharedPreferences = this.getSharedPreferences("data",MODE_PRIVATE);

        json = sharedPreferences.getString("object","");
        studentObject = gson.fromJson(json,StudentObject.class);

        recyclerView.setLayoutManager(new LinearLayoutManager(StudentInventory.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        studentInventoryRecords = studentObject.getStudentInventoryRecords();

        ArrayList<StudentInventoryRecord> list = new ArrayList<>();
        for(StudentInventoryRecord x : studentInventoryRecords){
            if(!x.getFrom().isEmpty()){
                list.add(new StudentInventoryRecord(x.getFrom(),x.getTo(),x.getStudentRollno(),x.getItem(),x.getValue()));
            }
        }

        adapter = new StudentInventoryAdapter(StudentInventory.this , list);
        recyclerView.setAdapter(adapter);

    }
}
