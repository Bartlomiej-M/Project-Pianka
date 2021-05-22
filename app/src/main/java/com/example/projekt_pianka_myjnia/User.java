package com.example.projekt_pianka_myjnia;

public class User {

    String profil_picture;

    public String getProfil_picture() {
        return profil_picture;
    }

    public void setProfil_picture(String profil_picture) {
        this.profil_picture = profil_picture;
    }


    String fullname;
    String username;
    String email;
    int brudaski_score;

    public User() {

    }

    public User(int brudaski_score, String fullname, String username, String email, String profil_picture) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.brudaski_score = brudaski_score;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getbrudaski() {
        return brudaski_score;
    }

    public void setbrudaski(int brudaski_score) {
        this.brudaski_score = brudaski_score;
    }
}