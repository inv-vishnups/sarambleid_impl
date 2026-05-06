package com.inv.scrambleid.controller;


import com.inv.scrambleid.forms.UserForm;
import com.inv.scrambleid.service.UserService;
import com.inv.scrambleid.view.UserView;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserView createUser(@Valid @RequestBody UserForm userForm) {
        return userService.createUser(userForm);
    }
}
