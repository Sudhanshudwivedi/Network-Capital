package com.example.android.networkcapital.Modules;

/**
 * Created by Nimesh Sachan on 6/19/2018.
 */

public class posts {

    public String description;
    public String name;
    public String user_id;

    public posts(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public posts(String desc, String name, String user_id) {
        this.description = desc;
        this.name = name ;
        this.user_id = user_id;
    }


}
