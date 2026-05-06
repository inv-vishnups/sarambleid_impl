package com.inv.scrambleid.mapping;


import com.inv.scrambleid.entity.Email;
import com.inv.scrambleid.entity.Name;
import com.inv.scrambleid.entity.User;
import com.inv.scrambleid.forms.EmailsForm;
import com.inv.scrambleid.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    private final EmailMapper emailMapper;
    private final NameMapper nameMapper;

    public UserMapper(EmailMapper emailMapper, NameMapper nameMapper) {
        this.emailMapper = emailMapper;
        this.nameMapper = nameMapper;
    }

    public User toEntity(UserForm userForm) {
        User user = new User();

        user.setUserName(userForm.userName());
        user.setRoles(userForm.roles());

        // Emails
        List<Email> emailList = userForm.emails()
                .stream()
                .map(emailMapper::toEmail)
                .toList();

        emailList.forEach(email -> email.setUser(user));
        user.setEmails(emailList);

        // Name (🔥 correct bidirectional setup)
        if (userForm.name() != null) {
            Name name = nameMapper.toEntity(userForm.name(), user);
            user.setName(name);
        }

        return user;
    }
}
