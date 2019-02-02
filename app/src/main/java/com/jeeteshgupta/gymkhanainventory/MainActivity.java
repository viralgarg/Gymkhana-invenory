package com.jeeteshgupta.gymkhanainventory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;

import javax.crypto.Mac;

public class MainActivity extends AppCompatActivity implements  Serializable{


    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String userName = null;
    private String PassWord = null;

    private EditText rollno;
    private EditText password;

    private CheckBox student;
    private CheckBox coordinator;
    private CheckBox gsec;

    private Button login ;
    private Button signUp ;

    private int checkNo ;
    private SharedPreferences sharedPreferences;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        Intent intent = new Intent( MainActivity.this , GiveInventory.class);
//        startActivity(intent);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        sharedPreferences = this.getSharedPreferences( "data"  ,MODE_PRIVATE);

        String is_logged_in = sharedPreferences.getString("login" ,"0");
        if(is_logged_in.equals("yes")){
            String type = sharedPreferences.getString("type","");
            if(type.equals("Coordinator")){
                startActivity(new Intent(MainActivity.this,CoordinatorActivity.class));
            }else{
                startActivity(new Intent(MainActivity.this,StudentActivity.class));
            }
        }

        signUp = (Button) findViewById(R.id.signUp);
        login  = (Button) findViewById(R.id.login);

        coordinator = (CheckBox) findViewById(R.id.coordinator);
        student     = (CheckBox) findViewById(R.id.student);
        gsec        = (CheckBox) findViewById(R.id.gsec);

        password    = (EditText) findViewById(R.id.password);
        rollno       = (EditText) findViewById(R.id.rollno);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !rollno.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){

                    checkNo = 0;

                    if(student.isChecked()){
                        checkNo++;
                    }
                    if(coordinator.isChecked()){
                        checkNo++;
                    }
                    if(gsec.isChecked()){
                        checkNo++;
                    }
                    //Check the no of feilds...
                    if(checkNo > 1){
                        Toast.makeText(getBaseContext(),"Please Select One feild" , Toast.LENGTH_LONG).show();
                    }else {
                        if(checkNo == 1){
                            spinner.setVisibility(View.VISIBLE);

                            userName = rollno.getText().toString().toUpperCase();
                            PassWord = password.getText().toString();

                            if(gsec.isChecked()){
                                userName += "@lnmiit.ac.in";
                                mAuth.signInWithEmailAndPassword(userName, PassWord)
                                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Intent intent = new Intent(MainActivity.this , Gsec.class);
                                                    startActivity(intent);
                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    Toast.makeText(getBaseContext(), "Invalid Credentials.",
                                                            Toast.LENGTH_SHORT).show();
                                                    //updateUI(null);
                                                }
                                                spinner.setVisibility(View.GONE);
                                                // ...
                                            }
                                        });
                            }

                            if(coordinator.isChecked()) {
                                //If coordinators signs in..

                                DatabaseReference mref = FirebaseDatabase.getInstance().getReference("Coordinators").child(userName);

                                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       CoordinatorObject coordinatorObject = dataSnapshot.getValue(CoordinatorObject.class);
                                        if(coordinatorObject == null){
                                            //No user for this Roll no..
                                            Toast.makeText(getBaseContext(),"Invalid Account",Toast.LENGTH_SHORT).show();
                                        }else{
                                            if(PassWord.equals(coordinatorObject.getPassword())){
                                                Intent intent = new Intent(MainActivity.this,CoordinatorActivity.class);
                                                Gson gson = new Gson();
                                                String json = gson.toJson(coordinatorObject);
                                                sharedPreferences.edit().putString("object" , json).commit();
                                                sharedPreferences.edit().putString("login" , "yes" ).commit();
                                                sharedPreferences.edit().putString("type" , "Coordinator").commit();
                                                spinner.setVisibility(View.GONE);
                                                startActivity(intent);

                                            }else{
                                                Toast.makeText(getBaseContext(), " Invalid User " ,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            if(student.isChecked()){
                                DatabaseReference mref = FirebaseDatabase.getInstance().getReference("Students").child(userName);

                                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        StudentObject studentObject = dataSnapshot.getValue(StudentObject.class);
                                        if( studentObject == null ){
                                            //No user..
                                            Toast.makeText(getBaseContext(),"Invalid Account" , Toast.LENGTH_SHORT).show();
                                        }else{
                                            if(PassWord.equals(studentObject.getPassword())){
                                                Gson gson = new Gson();
                                                String json = gson.toJson(studentObject);

                                                sharedPreferences.edit().putString("object",json).commit();
                                                sharedPreferences.edit().putString("login","yes").commit();
                                                sharedPreferences.edit().putString("type","student").commit();

                                                spinner.setVisibility(View.GONE);
                                                startActivity(new Intent(MainActivity.this , StudentActivity.class));

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                        }else{
                            Toast.makeText(getBaseContext(), "Please Select One Feild" , Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{
                    Toast.makeText(getBaseContext(),"Please Enter Details" ,Toast.LENGTH_LONG).show();
                }

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,RegisterStudent.class);
                startActivity(i);
            }
        });


    }

}
