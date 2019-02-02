package com.jeeteshgupta.gymkhanainventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.sql.Ref;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class GiveInventory extends AppCompatActivity implements Serializable {

    private TextView item,comment,roll;
    private EditText val,editrollno;

    private int position,givequnat,initialcount;
    private CoordinatorObject coordinatorObject;
    private String rollno;

    private StudentObject studentObject;

    private ArrayList<Inventory> list;
    private ArrayList<StudentInventoryRecord> studentInventoryRecords;
    private ArrayList<StudentInventoryRecord> myInventory;

    private SharedPreferences sharedPreferences;

    DatabaseReference ref;

    private Button change;

    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_inventory);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        sharedPreferences = this.getSharedPreferences("data",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("object","");
        coordinatorObject = gson.fromJson(json,CoordinatorObject.class);

        item = (TextView) findViewById(R.id.item);
        comment=(TextView) findViewById(R.id.comment);
        val  = (EditText) findViewById(R.id.value);
        roll=(TextView) findViewById(R.id.roll);
        editrollno=(EditText)findViewById(R.id.editRollno);


        position = (int) getIntent().getSerializableExtra("position");

        list = coordinatorObject.getInventory();

        initialcount = list.get(position).getValue();
        item.setText( list.get(position).getName().toString() );
        val.setText("0");

        change = (Button) findViewById(R.id.submit);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rollno=editrollno.getText().toString().toUpperCase();

                if( val.getText().toString().isEmpty() ){
                    Toast.makeText(getBaseContext(),"Please Fill Feilds ", Toast.LENGTH_SHORT).show();
                }else{
                    spinner.setVisibility(View.VISIBLE);
                    givequnat = Integer.parseInt(val.getText().toString());

                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Students");
                    myRef.child(rollno).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            studentObject = dataSnapshot.getValue(StudentObject.class);
                            if(studentObject != null){

                                if(initialcount >= givequnat){
                                    initialcount -= givequnat;

                                    Inventory item = list.get(position);
                                    item.setValue(initialcount);

                                    list.set(position,item);
                                    coordinatorObject.setInventory(list);

                                    studentInventoryRecords = coordinatorObject.getStudentInventoryRecords();
                                    studentInventoryRecords.add( new StudentInventoryRecord(coordinatorObject.getName(),
                                            studentObject.getName(),
                                            studentObject.getRollNo(),
                                            item.getName(),
                                            givequnat) );

                                    coordinatorObject.setStudentInventoryRecords(studentInventoryRecords);

                                    //Changing the sharedPreferences...
                                    Gson newgson   = new Gson();
                                    String newjson = newgson.toJson(coordinatorObject);
                                    sharedPreferences.edit().putString("object",newjson).commit();

                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Coordinators");
                                    ref.child(coordinatorObject.getRollNo()).setValue(coordinatorObject);

                                    myInventory = studentObject.getStudentInventoryRecords();
                                    myInventory.add(new StudentInventoryRecord( coordinatorObject.getName() ,
                                            studentObject.getName() ,
                                            studentObject.getRollNo(),
                                            item.getName() ,
                                            givequnat));

                                    studentObject.setStudentInventoryRecords(myInventory);

                                    ref = FirebaseDatabase.getInstance().getReference("Students");
                                    ref.child(studentObject.getRollNo()).setValue(studentObject);

                                    Toast.makeText(getBaseContext(),"Inventory Given Successfully",Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(GiveInventory.this,CoordinatorActivity.class);
                                    spinner.setVisibility(View.GONE);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(getBaseContext()," Not Enough " + list.get(position).getName() , Toast.LENGTH_SHORT).show();
                                }


                            }else{
                                Toast.makeText(getBaseContext(),"Invalid Student",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

        });

    }
}
