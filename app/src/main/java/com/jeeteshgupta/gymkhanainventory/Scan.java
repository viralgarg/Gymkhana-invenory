package com.jeeteshgupta.gymkhanainventory;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

public class Scan extends AppCompatActivity implements Serializable {

    private CoordinatorObject coordinatorObject;

    private RecyclerView recyclerView;
    private ArrayList<Inventory> list;
    private InventoryAdapterScan adapter;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        sharedPreferences = this.getSharedPreferences("data",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("object","");
        coordinatorObject = gson.fromJson(json,CoordinatorObject.class);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(Scan.this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);

        ArrayList<Inventory> ll = new ArrayList(coordinatorObject.getInventory());

        list = new ArrayList<>();
        for(Inventory x:ll) {
            if (!x.getName().isEmpty())
                list.add(x);
        }
        adapter = new InventoryAdapterScan(Scan.this,list,coordinatorObject);
        recyclerView.setAdapter(adapter);

    }
}
