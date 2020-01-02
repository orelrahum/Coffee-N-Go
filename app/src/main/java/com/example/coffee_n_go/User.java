package com.example.coffee_n_go;

public class User {

    private String Name;
    private String Email;
    private String PhoneNumber;
    private String AuthID;
    private String Permissions;

    public User() { }

    public User(String name,String email, String phoneNumber,String authID,String permissions){
        Name=name;
        Email=email;
        PhoneNumber=phoneNumber;
        AuthID=authID;
        Permissions=permissions;
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

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAuthID() {
        return AuthID;
    }

    public void setAuthID(String authID) {
        AuthID = authID;
    }

    public String getPermissions() {
        return Permissions;
    }

    public void setPermissions(String permissions) {
        Permissions = permissions;
    }
}
