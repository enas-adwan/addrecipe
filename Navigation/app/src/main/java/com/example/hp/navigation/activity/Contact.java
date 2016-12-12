package com.example.hp.navigation.activity;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Contact {

    //private variables
    String _d;
    String _name;
    int _id;
    Float _calory;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String name,Float calory,String  d){
        this._id = id;
       this._name = name;
        this._calory=calory;
        this._d = d;
    }


    // constructor
    public Contact(String name,Float calory,String d){
        this._name = name;
        this._calory=calory;
          this._d = d;
    }
    // getting ID

    public float getCalory(){
        return this._calory;
    }

    // setting id
    public void setCalory(float calory){
        this._calory = calory;
    }
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    String  getDateTime() {
        return _d;
    }

    public void setDate(String  d){
        this._d = d;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

}