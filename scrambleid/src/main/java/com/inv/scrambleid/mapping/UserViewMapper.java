package com.inv.scrambleid.mapping;

import com.inv.scrambleid.entity.Email;
import com.inv.scrambleid.entity.User;
import com.inv.scrambleid.view.EmailView;
import com.inv.scrambleid.view.NameView;
import com.inv.scrambleid.view.UserView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserViewMapper {

    public UserView toView(User user) {

        NameView nameView = null;
        if (user.getName() != null) {
            nameView = new NameView(
                    user.getName().getGivenName(),
                    user.getName().getFamilyName()
            );
        }

        List<EmailView> emailViews = user.getEmails()
                .stream()
                .map(this::mapEmail)
                .toList();

        return new UserView(
                user.getUserId(),
                user.getUserName(),
                nameView,
                emailViews,
                user.getRoles()
        );
    }

    private EmailView mapEmail(Email email) {
        return new EmailView(
                Boolean.valueOf(email.getPrimary()), // if still String
                email.getValue(),
                email.getType()
        );
    }
}