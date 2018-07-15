package com.example.android.networkcapital.Modules;



public class posts {

    public String description;
    public String name;
    public String timestamp;
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

    public posts(String desc, String name, String timestamp, String thumb_image ){
        this.description = desc;
        this.name = name ;
        this.timestamp= timestamp;
        this.thumb_image = thumb_image;
    }


}
