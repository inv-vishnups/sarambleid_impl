package com.inv.scrambleid.service;


import com.inv.scrambleid.forms.RegisterRequest;

public interface ScrambleUserService {

    String createUser(RegisterRequest request);
}