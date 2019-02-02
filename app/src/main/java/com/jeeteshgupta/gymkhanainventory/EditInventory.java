package com.jeeteshgupta.gymkhanainventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class EditInventory extends AppCompatActivity {

    private TextView item;
    private EditText val;

    private int position;
    private CoordinatorObject coordinatorObject;

    private SharedPreferences sharedPreferences;

    ArrayList<Inventory> list;

    private Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inventory);

        sharedPreferences = this.getSharedPreferences("data" , MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("object","");
        coordinatorObject = gson.fromJson(json,CoordinatorObject.class);

        item = (TextView) findViewById(R.id.item);
        val  = (EditText) findViewById(R.id.value);

        list = coordinatorObject.getInventory();
        position = (int) getIntent().getSerializableExtra("position");

        item.setText( list.get(position+1).getName().toString() );
        val.setText( Integer.toString(list.get(position+1).getValue() ) );

        change = (Button) findViewById(R.id.submit);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( val.getText().toString().isEmpty() ){
                    Toast.makeText(getBaseContext(),"Please Fill Feilds",Toast.LENGTH_SHORT).show();
                }else{
                    Inventory obj = list.get(position+1);
                    obj.setValue(parseInt(val.getText().toString()));

                    if(val.getText().toString().equals("0")){
                        list.remove(position+1);
                    }else{
                        list.set(position+1, obj);
                    }
                    coordinatorObject.setInventory(list);

                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Coordinators");
                    myRef.child(coordinatorObject.getRollNo()).setValue(coordinatorObject);

                    Gson newgson   = new Gson();
                    String newjson = newgson.toJson(coordinatorObject);
                    sharedPreferences.edit().putString("object",newjson).commit();

                    Toast.makeText(getBaseContext() , "Successfully Updated " , Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(EditInventory.this , CoordinatorActivity.class));
                }
            }
        });

    }
}
