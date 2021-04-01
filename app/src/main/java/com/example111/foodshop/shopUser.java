package com.example111.foodshop;

public class shopUser {
        String Contact;
        String Email;
        String Name;
        String Uid;

    public shopUser() {
    }

    public shopUser(String contact, String email, String name,String uid) {
        Contact = contact;
        Email = email;
        Name = name;
        Uid=uid;
    }

    public String getContact() {
        return Contact;
    }

    public String getEmailid() {
        return Email;
    }

    public String getNameuser() {
        return Name;
    }
}
