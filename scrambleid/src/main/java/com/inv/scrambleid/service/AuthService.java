package com.inv.scrambleid.service;


import com.inv.scrambleid.forms.LoginRequest;
import com.inv.scrambleid.forms.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    String login(LoginRequest request);
}
