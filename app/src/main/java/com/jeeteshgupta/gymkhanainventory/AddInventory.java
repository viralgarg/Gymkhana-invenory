package com.jeeteshgupta.gymkhanainventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddInventory extends AppCompatActivity implements Serializable {

    private CoordinatorObject coordinatorObject;
    private SharedPreferences sharedPreferences;

    RecyclerView recyclerView;
    ArrayList<Inventory> list;
    InventoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = this.getSharedPreferences("data", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("object", "");
        coordinatorObject = gson.fromJson(json, CoordinatorObject.class);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(AddInventory.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        ArrayList<Inventory> ll = coordinatorObject.getInventory();

        list = new ArrayList<>();
        for (Inventory x : ll) {
            if (!x.getName().isEmpty())
                list.add(x);
        }
        adapter = new InventoryAdapter(AddInventory.this, list);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
            Intent intent = new Intent(AddInventory.this, addItem.class);
            startActivity(intent);
        }
        });

    }

}
