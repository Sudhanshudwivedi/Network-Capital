package com.example.android.networkcapital;

public class HUsers {

    public String name;
    public String thumb_image;



    public HUsers(){

    }

    public HUsers(String name,String thumb_image) {
        this.name = name;
        this.thumb_image=thumb_image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }




}