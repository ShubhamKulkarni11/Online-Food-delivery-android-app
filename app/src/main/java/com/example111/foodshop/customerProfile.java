package com.example111.foodshop;

public class customerProfile {
    String Name;
    String Email;
    String Contact;

    public customerProfile() {
    }

    public customerProfile(String name, String email, String contact) {
        Name = name;
        Email = email;
        Contact = contact;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getContact() {
        return Contact;
    }
}
