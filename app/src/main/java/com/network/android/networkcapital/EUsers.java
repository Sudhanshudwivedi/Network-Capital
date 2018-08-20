package com.network.android.networkcapital;

public class EUsers {

    public String name;
    public String thumb_image;
    public String position;



    public EUsers(){

    }

    public EUsers(String name,String thumb_image,String position) {
        this.name = name;
        this.thumb_image=thumb_image;
        this.position=position;

    }

    public String getPosition()
    {return position;}

    public String getName() {
        return name;
    }

    public void setPosition(String position) {
        this.position = position;
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