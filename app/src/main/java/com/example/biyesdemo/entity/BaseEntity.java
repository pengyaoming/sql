package com.example.biyesdemo.entity;

public class BaseEntity {
    private int id;
    private String username;
    private String phone;
    private String price;
    private String number;
    private String total;
    private String name;
    private String time;

    public BaseEntity(String username, String phone, String price, String number, String total, String name, int id, String time) {
        this.username = username;
        this.phone = phone;
        this.price = price;
        this.number = number;
        this.total = total;
        this.name = name;
        this.id = id;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean contains(int charString) {
        if (charString == id) {
            return true;
        } else {
            return false;
        }
    }
}
