package com.jeeteshgupta.gymkhanainventory;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Gsec extends AppCompatActivity {

    private String name ,  password , hostel , council , phoneNo , rollno;

    private EditText username , Password , mobileNo , Name ;
    private Spinner  hostels , councils;
    private Button   add;

    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsec);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        username = (EditText) findViewById(R.id.rollno);
        Password = (EditText) findViewById(R.id.password);
        mobileNo = (EditText) findViewById(R.id.phoneNo);
        Name     = (EditText) findViewById(R.id.name);

        hostels  = (Spinner) findViewById(R.id.hostel);
        councils = (Spinner) findViewById(R.id.Council);

        add = (Button) findViewById(R.id.signUp);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !username.getText().toString().isEmpty() && !Password.getText().toString().isEmpty()
                        && !mobileNo.getText().toString().isEmpty() ){
                    spinner.setVisibility(View.VISIBLE);
                    rollno   = username.getText().toString().toUpperCase();
                    password = Password.getText().toString();
                    phoneNo  = mobileNo.getText().toString();
                    name     = Name.getText().toString();

                    hostel   = hostels.getSelectedItem().toString();
                    council  = councils.getSelectedItem().toString();


                    //Add to the Coordinators Table..
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Coordinators");
                    myRef.child(rollno).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CoordinatorObject object = dataSnapshot.getValue(CoordinatorObject.class);
                            if(object == null){
                                CoordinatorObject obj = new CoordinatorObject();
                                obj.setName(name);
                                obj.setRollNo(rollno);
                                obj.setPassword(password);
                                obj.setCouncil(council);
                                obj.setPhoneNo(phoneNo);
                                obj.setHostel(hostel);

                                ArrayList<Inventory> inventory = new ArrayList<>();
                                inventory.add(new Inventory());

                                ArrayList<StudentInventoryRecord> studentInventoryRecords = new ArrayList<StudentInventoryRecord>();
                                studentInventoryRecords.add(new StudentInventoryRecord());

                                obj.setInventory(inventory);
                                obj.setStudentInventoryRecords(studentInventoryRecords);
                                //Push it into database...
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                ref.child("Coordinators").child(rollno).setValue(obj);

                                Toast.makeText(getBaseContext()," Successfully Added " , Toast.LENGTH_SHORT).show();

                                spinner.setVisibility(View.GONE);


                                Name.setText("");
                                username.setText("");
                                Password.setText("");
                                mobileNo.setText("");
                            }else {
                                Toast.makeText(getBaseContext()," Already Registered " , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else{
                    Toast.makeText(getBaseContext(),"Please Fill All Feilds",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
