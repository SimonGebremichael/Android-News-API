package com.example.sg_finalassign_sg;

public class User {

    public int id;
    public String name;
    public String email;
    public String phone;
    public String password;
    public article[] liked = new article[10];
    public article[] disliked = new article[10];
    public article[] saved = new article[10];
    public String interests;

    public User() { }

    public User(int id, String name, String password, String email, String phone, String interests) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.interests = interests;
    }

    public User(String name, String password, String email, String phone, String interests) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.interests = interests;
    }

    public int getId() {
        return id;
    }

    public String getInterests() {
        return interests;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public article[] getLiked() {
        return liked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLiked(article[] liked) {
        this.liked = liked;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
