package com.App.Gangesh.Shubham;

/**
 * Created by jake on 1/10/18.
 */

public class Players {
    String name;
    String country;
    int age,id,totScore;
    float price;
    Long count=Long.valueOf(0);
    String Role;
    String Team;
    String url;



    public Players() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setCount(Long count) {
        this.count = count;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setTotScore(int totScore) {
        this.totScore = totScore;
    }

    public void setRole(String role) {
        Role = role;
    }


    public void setTeam(String team) {
        Team = team;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
    public Long getCount() {
        return count;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public int getTotScore() {
        return totScore;
    }

    public String getRole() {
        return Role;
    }

    public String getTeam() {
        return Team;
    }
}
