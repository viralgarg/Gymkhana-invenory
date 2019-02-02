package com.jeeteshgupta.gymkhanainventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class addItem extends AppCompatActivity implements Serializable{

    private EditText item , count;
    private Button   add;
    private SharedPreferences sharedPreferences;
    private ArrayList<Inventory> inventory;

    private CoordinatorObject coordinatorObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        sharedPreferences = this.getSharedPreferences("data",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("object","");
        coordinatorObject = gson.fromJson(json,CoordinatorObject.class);

        item  = (EditText) findViewById(R.id.item);
        count = (EditText) findViewById(R.id.count);

        add   = (Button)   findViewById(R.id.add);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( item.getText().toString().isEmpty() || count.getText().toString().isEmpty() ){
                    Toast.makeText(getBaseContext(),"Please Fill all Feilds" , Toast.LENGTH_LONG).show();
                }else{
                    inventory = coordinatorObject.getInventory();
                    inventory.add(new Inventory(item.getText().toString(), parseInt(count.getText().toString()) ));

                    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("Coordinators");
                    myref.child(coordinatorObject.getRollNo()).child("inventory").setValue(inventory);
                    Toast.makeText(getBaseContext(), "Successfully Added " , Toast.LENGTH_SHORT).show();

                    Gson newgson = new Gson();
                    String newjson = newgson.toJson(coordinatorObject);
                    sharedPreferences.edit().putString("object",newjson).commit();

                    startActivity(new Intent(addItem.this,AddInventory.class));

                }
            }
        });

    }
}
