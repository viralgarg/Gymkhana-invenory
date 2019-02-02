package com.jeeteshgupta.gymkhanainventory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterStudent extends AppCompatActivity {

    private EditText rollNo , password , mobileNo , name ;
    private String RollNo , Password , MobileNo , Name ;
    private Button signup;
    private int student_exists;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        rollNo=(EditText) findViewById(R.id.rollno);
        password=(EditText) findViewById(R.id.password);
        mobileNo=(EditText) findViewById(R.id.phoneNo);
        name=(EditText) findViewById(R.id.name);

        signup=(Button)findViewById(R.id.signUp);

        student_exists = 0;

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !rollNo.getText().toString().isEmpty() &&!name.getText().toString().isEmpty() &&
                        !password.getText().toString().isEmpty() && !mobileNo.getText().toString().isEmpty() ) {

                    spinner.setVisibility(View.VISIBLE);

                    RollNo   = rollNo.getText().toString().toUpperCase();
                    Password = password.getText().toString();
                    MobileNo  = mobileNo.getText().toString();
                    Name     = name.getText().toString();


                    //Add to the Coordinators Table..
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Students");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(RollNo)){
                                Toast.makeText(getBaseContext(), " Student Already Exists " ,Toast.LENGTH_SHORT).show();
                            }else{
                                DatabaseReference myReff = FirebaseDatabase.getInstance().getReference();
                                StudentObject obj = new StudentObject();

                                obj.setName(Name);
                                obj.setRollNo(RollNo);
                                obj.setPassword(Password);
                                obj.setPhoneNo(MobileNo);

                                ArrayList<StudentInventoryRecord> studentInventoryRecords = new ArrayList<>();
                                studentInventoryRecords.add(new StudentInventoryRecord());
                                obj.setStudentInventoryRecords(studentInventoryRecords);

                                myReff.child("Students").child(RollNo).setValue(obj);
                                Toast.makeText(getBaseContext(), "Successfully Registered.. ", Toast.LENGTH_SHORT).show();


                                rollNo.setText("");
                                password.setText("");
                                mobileNo.setText("");
                                name.setText("");

                                Intent intent = new Intent(RegisterStudent.this,MainActivity.class);
                                spinner.setVisibility(View.GONE);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    Toast.makeText(getBaseContext(),"Please Fill All Feilds",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
