package com.example.lostandfoundapp;

public class Strings {
    String item1;
    String item2;
    //initiate them
    public Strings(String item1, String item2) {
        this.item1 = item1;
        this.item2 = item2;
    }
    //declare the returns so that it sets them
    public String getItem1() {
        return item1;
    }
    public void setItem1(String item1) {
        this.item1 = item1;
    }
    public String getItem2() {
        return item2;
    }
    public void setItem2(String item2) {
        this.item2 = item2;
    }
}