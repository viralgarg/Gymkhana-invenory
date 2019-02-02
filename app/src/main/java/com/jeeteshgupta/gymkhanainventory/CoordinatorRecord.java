package com.jeeteshgupta.gymkhanainventory;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class CoordinatorRecord extends AppCompatActivity implements Serializable{

    private RecyclerView recyclerView;
    private CoordinatorObject coordinatorObject;
    private ArrayList<StudentInventoryRecord> studentInventoryRecords;
    private CoordinatorRecordAdpter adpater;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_record);

        sharedPreferences = this.getSharedPreferences("data",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("object","");
        coordinatorObject = gson.fromJson(json,CoordinatorObject.class);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(CoordinatorRecord.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        studentInventoryRecords = coordinatorObject.getStudentInventoryRecords();

        ArrayList<StudentInventoryRecord> list = new ArrayList<StudentInventoryRecord>();

        for (StudentInventoryRecord x : studentInventoryRecords) {
            if (!x.getFrom().isEmpty()) {
                list.add(new StudentInventoryRecord(x.getFrom(), x.getTo(), x.getStudentRollno(), x.getItem(), x.getValue()));
            }
        }

        adpater = new CoordinatorRecordAdpter(CoordinatorRecord.this, list, coordinatorObject);
        recyclerView.setAdapter(adpater);


    }
}
