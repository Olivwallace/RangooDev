package com.example.rangoo.Model;

public class LoginData {
    private String email;
    private String password;

    public LoginData(String email, String password){
        setEmail(email);
        setPassword(password);
    }

    // ---------------- Setter's and Getter's

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
