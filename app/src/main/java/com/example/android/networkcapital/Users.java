package com.example.android.networkcapital;

/**
 * Created by Sudhanshu on 11-Oct-17.
 */

public class Users {

    public String user_id;
    public String desc;




    public Users(){

    }

    public Users(String user_id, String desc) {
        this.user_id = user_id;
        this.desc = desc;

    }

    public String getId() {
        return user_id;
    }

    public void setId(String name) {
        this.user_id = name;
    }

    public String getdesc() {
        return desc;
    }

    public void setdesc(String desc) {
        this.desc = desc;
    }



}