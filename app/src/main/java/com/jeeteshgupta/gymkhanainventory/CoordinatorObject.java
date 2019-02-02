package com.jeeteshgupta.gymkhanainventory;

import java.io.Serializable;
import java.util.ArrayList;

class CoordinatorObject implements Serializable{
    private String Name;
    private String RollNo;
    private String PhoneNo;
    private String Hostel;
    private String Council;
    private String Password;
    private ArrayList<Inventory> inventory ;
    private ArrayList<StudentInventoryRecord> studentInventoryRecords;

    public CoordinatorObject(){
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String rollNo) {
        RollNo = rollNo;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getHostel() {
        return Hostel;
    }

    public void setHostel(String hostel) {
        Hostel = hostel;
    }

    public String getCouncil() {
        return Council;
    }

    public void setCouncil(String council) {
        Council = council;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public ArrayList<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Inventory> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<StudentInventoryRecord> getStudentInventoryRecords() {
        return studentInventoryRecords;
    }

    public void setStudentInventoryRecords(ArrayList<StudentInventoryRecord> studentInventoryRecords) {
        this.studentInventoryRecords = studentInventoryRecords;
    }
}
