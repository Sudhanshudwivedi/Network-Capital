package com.example.android.networkcapital.Modules;

/**
 * Created by Nimesh Sachan on 6/19/2018.
 */

public class posts {

    public String description;
    public String name;
    public String user_id;
    public String thumb_image;

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
    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public posts(String desc, String name, String user_id, String thumb_image ){
        this.description = desc;
        this.name = name ;
        this.user_id = user_id;
        this.thumb_image = thumb_image;
    }


}
