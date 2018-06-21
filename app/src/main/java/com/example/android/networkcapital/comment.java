package com.example.android.networkcapital;

public class comment {

    public String comments;
    public String name;
    public String date;
    public String time;

    public comment (){

    }

    public String getcomments() {
        return comments;
    }

    public void setcomments(String comments) {
        this.comments= comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }
    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public comment (String comments, String name, String date, String time ){
        this.comments = comments;
        this.name = name ;
        this.date= date;
        this.time = time;
    }


}
