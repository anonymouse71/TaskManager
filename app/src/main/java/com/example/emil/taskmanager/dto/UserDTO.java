package com.example.emil.taskmanager.dto;

public class UserDTO {

    private String _id;
    private String email;
    private String password;

    public UserDTO(String _id, String email, String password) {
        this._id = _id;
        this.email = email;
        this.password = password;
    }

    public String getID() {
        return _id;
    }

    public void setID(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
