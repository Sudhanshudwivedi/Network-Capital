package com.example.android.networkcapital;

/**
 * Created by Sudhanshu on 11-Oct-17.
 */

public class Users {

    public String name;
    public String comment;
    public String timestamp;
    public String thumb_image;



    public Users(){

    }

    public Users(String name, String comment, String timestamp, String thumb_image) {
        this.name = name;
        this.comment = comment;
        this.timestamp = timestamp;
        this.thumb_image = thumb_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

}