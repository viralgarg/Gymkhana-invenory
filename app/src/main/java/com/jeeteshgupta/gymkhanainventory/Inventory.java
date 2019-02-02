package com.jeeteshgupta.gymkhanainventory;

import java.io.Serializable;

public class Inventory implements Serializable{
    private String name;
    private int value;

    public  Inventory(String name , int value){
        this.name = name;
        this.value = value;
    }
    public  Inventory(){
        this.name = "";
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }
}
