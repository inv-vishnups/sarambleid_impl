package com.inv.scrambleid.service;

import com.inv.scrambleid.forms.UserForm;
import com.inv.scrambleid.view.UserView;

public interface UserService {
    UserView createUser(UserForm userForm);
}
