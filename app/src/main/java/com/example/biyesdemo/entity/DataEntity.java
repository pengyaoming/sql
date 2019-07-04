package com.example.biyesdemo.entity;

public class DataEntity {
    private String name;
    private String phone;

    public DataEntity(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public DataEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
