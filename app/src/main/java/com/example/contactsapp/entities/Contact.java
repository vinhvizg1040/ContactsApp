package com.example.contactsapp.entities;

import java.io.Serializable;
import java.util.Date;

public class Contact implements Serializable {
    int Id;
    String Name;
    String Email;
    String Phone;

    public Contact() {
    }

    public Contact(int id, String name, String email, String phone) {
        Id = id;
        Name = name;
        Email = email;
        Phone = phone;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
