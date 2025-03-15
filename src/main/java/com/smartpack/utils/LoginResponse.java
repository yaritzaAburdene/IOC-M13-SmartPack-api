package com.smartpack.utils;
import java.util.Date;

public class LoginResponse {
    private String token;

    private Date expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {  
        this.token = token;
    }

    public void setExpiresIn(Date expiresIn) { 
        this.expiresIn = expiresIn;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }
}
