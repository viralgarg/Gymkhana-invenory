package com.jeeteshgupta.gymkhanainventory;

import java.util.ArrayList;

public class StudentObject {

    private String Name;
    private String RollNo;
    private String PhoneNo;
    private String Password;

    private ArrayList<StudentInventoryRecord> studentInventoryRecords;

    public String getPassword() {
        return this.Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getRollNo() {
        return this.RollNo;
    }

    public void setRollNo(String rollNo) {
        this.RollNo = rollNo;
    }

    public String getPhoneNo() {
        return this.PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public ArrayList<StudentInventoryRecord> getStudentInventoryRecords() {
        return studentInventoryRecords;
    }

    public void setStudentInventoryRecords(ArrayList<StudentInventoryRecord> studentInventoryRecords) {
        this.studentInventoryRecords = studentInventoryRecords;
    }
}
