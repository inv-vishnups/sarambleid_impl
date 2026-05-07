package com.inv.scrambleid.service;


import com.inv.scrambleid.forms.LoginRequest;
import com.inv.scrambleid.forms.RegisterRequest;
import com.inv.scrambleid.view.LoginResponse;

public interface AuthService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
