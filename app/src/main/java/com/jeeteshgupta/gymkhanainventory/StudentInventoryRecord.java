package com.jeeteshgupta.gymkhanainventory;

import java.io.Serializable;
public class StudentInventoryRecord implements Serializable {

    private String to,from,item,studentRollno;
    private int value;

    public  StudentInventoryRecord(String from ,String to ,  String studentRollno  , String item ,int value){
        this.from = from;
        this.to=to;
        this.item=item;
        this.value = value;
        this.studentRollno = studentRollno;
    }

    public  StudentInventoryRecord(){
        this.to = "";
        this.from ="";
        this.item = "";
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStudentRollno() {
        return studentRollno;
    }

    public void setStudentRollno(String studentRollno) {
        this.studentRollno = studentRollno;
    }
}
